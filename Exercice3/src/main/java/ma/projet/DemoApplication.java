package ma.projet;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired private HommeService hommeService;
    @Autowired private FemmeService femmeService;
    @Autowired private MariageService mariageService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // --- 1. Création (10 Femmes, 5 Hommes) ---
        Femme f1 = createFemme("RAMI", "SALIMA", sdf.parse("1970-01-01")); // La plus âgée
        Femme f2 = createFemme("ALI", "AMAL", sdf.parse("1975-02-02"));
        Femme f3 = createFemme("ALAOUI", "WAFA", sdf.parse("1980-03-03"));
        Femme f4 = createFemme("ALAMI", "KARIMA", sdf.parse("1970-04-04"));
        Femme f5 = createFemme("SAMI", "SANA", sdf.parse("1982-05-05"));
        Femme f6 = createFemme("ZIRI", "FATIMA", sdf.parse("1985-06-06")); // Sera mariée 2 fois
        Femme f7 = createFemme("BAKKALI", "HIND", sdf.parse("1990-07-07"));
        Femme f8 = createFemme("KADIRI", "LINA", sdf.parse("1992-08-08"));
        Femme f9 = createFemme("TAZI", "MARYEM", sdf.parse("1995-09-09"));
        Femme f10 = createFemme("IDRISSI", "KENZA", sdf.parse("1998-10-10"));

        Homme h1 = createHomme("SAFI", "SAID", sdf.parse("1965-01-01")); // Sera marié 4 fois
        Homme h2 = createHomme("CHERKAOUI", "MOHAMED", sdf.parse("1970-02-02"));
        Homme h3 = createHomme("MRABET", "YOUSSEF", sdf.parse("1980-03-03"));
        Homme h4 = createHomme("FILALI", "OMAR", sdf.parse("1984-04-04"));
        Homme h5 = createHomme("BENNANI", "ADAM", sdf.parse("1990-05-05"));

        // --- 2. Création des Mariages ---

        // Mariage 1 (Échoué)
        createMariage(h1, f4, sdf.parse("1989-09-03"), sdf.parse("1990-09-03"), 0);
        // Mariage 2 (En cours)
        createMariage(h1, f1, sdf.parse("1990-09-03"), null, 4);
        // Mariage 3 (En cours)
        createMariage(h1, f2, sdf.parse("1995-09-03"), null, 2);
        // Mariage 4 (En cours)
        createMariage(h1, f3, sdf.parse("2000-11-04"), null, 3);

        // f6 (FATIMA ZIRI) se marie 2 fois
        createMariage(h3, f6, sdf.parse("2005-01-01"), null, 1);
        createMariage(h4, f6, sdf.parse("2010-01-01"), sdf.parse("2012-01-01"), 1); // Mariage 2 (échoué)

        // --- 3. Tests des fonctionnalités ---

        System.out.println("\n--- Test: Afficher la liste des femmes ---");
        femmeService.findAll().forEach(f -> System.out.println("  - " + f.getPrenom() + " " + f.getNom()));

        System.out.println("\n--- Test: Afficher la femme la plus âgée ---");
        Femme plusAgee = femmeService.findFemmeLaPlusAgee();
        System.out.println("  - " + plusAgee.getPrenom() + " " + plusAgee.getNom() + " (Née le " + sdf.format(plusAgee.getDateNaissance()) + ")");

        System.out.println("\n--- Req 1 (HommeService): Épouses de h1 (SAID) mariées entre 1990 et 1996 ---");
        Date d1 = sdf.parse("1990-01-01");
        Date d2 = sdf.parse("1996-12-31");
        List<Femme> epouses = hommeService.findEpousesEntreDates(h1, d1, d2);
        epouses.forEach(e -> System.out.println("  - " + e.getPrenom() + " " + e.getNom()));
        // Doit afficher SALIMA (1990) et AMAL (1995)

        System.out.println("\n--- Req 2a (FemmeService): Nbr d'enfants de f1 (SALIMA) (mariages débutés 1990-2010) ---");
        long nbrEnfants = femmeService.getNombreEnfantsEntreDates(f1.getId(), d1, sdf.parse("2010-12-31"));
        System.out.println("  - Nombre d'enfants : " + nbrEnfants); // Doit afficher 4

        System.out.println("\n--- Req 2b (FemmeService): Femmes mariées au moins 2 fois ---");
        List<Femme> femmesMultiples = femmeService.findFemmesMarieesAuMoinsDeuxFois();
        femmesMultiples.forEach(f -> System.out.println("  - " + f.getPrenom() + " " + f.getNom()));
        // Doit afficher FATIMA ZIRI

        System.out.println("\n--- Req 2c (FemmeService/Criteria): Hommes mariés à 4 femmes (mariages débutés 1980-2010) ---");
        long nbrHommes = femmeService.countHommesMariesNFoisEntreDates(4, sdf.parse("1980-01-01"), sdf.parse("2010-12-31"));
        System.out.println("  - Nombre d'hommes : " + nbrHommes); // Doit afficher 1 (SAID SAFI)

        System.out.println("\n--- Req 5 (HommeService): Afficher détails mariages de h1 (SAID SAFI) ---");
        hommeService.afficherDetailsMariages(h1);
        // Doit afficher le format de l'exercice (3 en cours, 1 échoué)
    }

    // --- Méthodes utilitaires pour créer les objets ---

    private Femme createFemme(String nom, String prenom, Date dateNaiss) {
        Femme f = new Femme();
        f.setNom(nom);
        f.setPrenom(prenom);
        f.setDateNaissance(dateNaiss);
        f.setAdresse("Adresse de " + prenom);
        f.setTelephone("0600000000");
        return femmeService.create(f);
    }

    private Homme createHomme(String nom, String prenom, Date dateNaiss) {
        Homme h = new Homme();
        h.setNom(nom);
        h.setPrenom(prenom);
        h.setDateNaissance(dateNaiss);
        h.setAdresse("Adresse de " + prenom);
        h.setTelephone("0611111111");
        return hommeService.create(h);
    }

    private Mariage createMariage(Homme h, Femme f, Date debut, Date fin, int nbrEnfants) {
        Mariage m = new Mariage();
        m.setHomme(h);
        m.setFemme(f);
        m.setDateDebut(debut);
        m.setDateFin(fin);
        m.setNbrEnfant(nbrEnfants);
        return mariageService.create(m);
    }
}