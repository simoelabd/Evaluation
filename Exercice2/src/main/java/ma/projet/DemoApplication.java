package ma.projet;

import ma.projet.entities.*;
import ma.projet.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired private EmployeService employeService;
    @Autowired private ProjetService projetService;
    @Autowired private TacheService tacheService;
    @Autowired private EmployeTacheService employeTacheService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // --- 1. Création des Employés ---
        Employe chef = new Employe();
        chef.setNom("El Alaoui");
        chef.setPrenom("Ahmed");
        chef.setTelephone("0611223344");
        employeService.create(chef);

        Employe dev1 = new Employe();
        dev1.setNom("Bennani");
        dev1.setPrenom("Fatima");
        dev1.setTelephone("0655667788");
        employeService.create(dev1);

        // --- 2. Création de Projet ---
        Projet projet = new Projet();
        projet.setNom("Gestion de stock");
        projet.setDateDebut(sdf.parse("2013-01-14"));
        projet.setDateFin(sdf.parse("2013-06-30"));
        projet.setChefDeProjet(chef); // Ahmed est le chef
        projetService.create(projet);

        // --- 3. Création des Tâches (planifiées) ---
        Tache t1 = tacheService.create(createTache("Analyse", sdf.parse("2013-02-01"), sdf.parse("2013-03-01"), 1200.0, projet));
        Tache t2 = tacheService.create(createTache("Conception", sdf.parse("2013-03-01"), sdf.parse("2013-04-01"), 1500.0, projet));
        Tache t3 = tacheService.create(createTache("Développement", sdf.parse("2013-04-01"), sdf.parse("2013-05-15"), 900.0, projet));
        Tache t4 = tacheService.create(createTache("Test", sdf.parse("2013-05-15"), sdf.parse("2013-06-01"), 800.0, projet));

        // --- 4. Assignation des tâches (réalisation) ---
        // Fatima (dev1) réalise les tâches
        employeTacheService.create(createEmployeTache(dev1, t1, sdf.parse("2013-02-10"), sdf.parse("2013-02-20")));
        employeTacheService.create(createEmployeTache(dev1, t2, sdf.parse("2013-03-10"), sdf.parse("2013-03-15")));
        employeTacheService.create(createEmployeTache(dev1, t3, sdf.parse("2013-04-10"), sdf.parse("2013-04-25")));

        // (La tâche t4 n'est pas encore réalisée)


        // --- Validation des fonctionnalités ---

        System.out.println("\n--- EmployeService: Projets gérés par Ahmed (chef) ---");
        List<Projet> projetsGeres = employeService.findProjetsGeresByEmploye(chef);
        projetsGeres.forEach(p -> System.out.println("  - " + p.getNom()));

        System.out.println("\n--- EmployeService: Tâches réalisées par Fatima (dev1) ---");
        List<EmployeTache> tachesDev1 = employeService.findTachesRealiseesByEmploye(dev1);
        tachesDev1.forEach(et -> System.out.println("  - " + et.getTache().getNom() +
                " (Fin réelle: " + sdf.format(et.getDateFinReelle()) + ")"));

        System.out.println("\n--- ProjetService: Tâches planifiées pour 'Gestion de stock' ---");
        List<Tache> tachesPlanifiees = projetService.findTachesPlanifieesByProjet(projet);
        tachesPlanifiees.forEach(t -> System.out.println("  - " + t.getNom()));

        System.out.println("\n--- ProjetService: Tâches réalisées pour 'Gestion de stock' (Format Exercice) ---");
        projetService.afficherDetailsTachesRealisees(projet);

        System.out.println("\n--- TacheService: Tâches > 1000 DH (Requête Nommée) ---");
        List<Tache> tachesChere = tacheService.findTachesPrixSup1000();
        tachesChere.forEach(t -> System.out.println("  - " + t.getNom() + " (" + t.getPrix() + " DH)"));

        System.out.println("\n--- TacheService: Tâches réalisées entre 01/01/2013 et 31/03/2013 ---");
        Date d1 = sdf.parse("2013-01-01");
        Date d2 = sdf.parse("2013-03-31");
        List<Tache> tachesEntreDates = tacheService.findTachesRealiseesEntreDates(d1, d2);
        tachesEntreDates.forEach(t -> System.out.println("  - " + t.getNom()));
        // Doit afficher Analyse et Conception
    }

    // --- Méthodes utilitaires pour créer les objets ---

    private Tache createTache(String nom, Date debut, Date fin, double prix, Projet p) {
        Tache t = new Tache();
        t.setNom(nom);
        t.setDateDebut(debut);
        t.setDateFin(fin);
        t.setPrix(prix);
        t.setProjet(p);
        return t;
    }

    private EmployeTache createEmployeTache(Employe e, Tache t, Date debutReel, Date finReel) {
        EmployeTachePK pk = new EmployeTachePK();
        pk.setEmployeId(e.getId());
        pk.setTacheId(t.getId());

        EmployeTache et = new EmployeTache();
        et.setPk(pk);
        et.setEmploye(e);
        et.setTache(t);
        et.setDateDebutReelle(debutReel);
        et.setDateFinReelle(finReel);
        return et;
    }
}