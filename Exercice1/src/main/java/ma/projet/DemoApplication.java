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

    @Autowired
    private CategorieService categorieService;
    @Autowired
    private ProduitService produitService;
    @Autowired
    private CommandeService commandeService;
    @Autowired
    private LigneCommandeService ligneCommandeService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // --- Création des données ---

        // 1. Catégories
        Categorie cat1 = new Categorie();
        cat1.setLibelle("PC");
        cat1.setCode("PC");
        categorieService.create(cat1);

        Categorie cat2 = new Categorie();
        cat2.setLibelle("Imprimante");
        cat2.setCode("IMP");
        categorieService.create(cat2);

        // 2. Produits
        Produit p1 = new Produit();
        p1.setReference("ES12");
        p1.setPrix(120);
        p1.setCategorie(cat1);
        produitService.create(p1);

        Produit p2 = new Produit();
        p2.setReference("ZR85");
        p2.setPrix(100);
        p2.setCategorie(cat1);
        produitService.create(p2);

        Produit p3 = new Produit();
        p3.setReference("EE85");
        p3.setPrix(200);
        p3.setCategorie(cat2);
        produitService.create(p3);

        Produit p4 = new Produit();
        p4.setReference("AZERTY");
        p4.setPrix(80);
        p4.setCategorie(cat2);
        produitService.create(p4);

        // 3. Commandes
        Commande c1 = new Commande();
        c1.setDate(sdf.parse("2013-03-14"));
        commandeService.create(c1);

        Commande c2 = new Commande();
        c2.setDate(sdf.parse("2013-05-20"));
        commandeService.create(c2);

        // 4. Lignes de Commande
        // Commande 1
        ligneCommandeService.create(createLigne(p1, c1, 7));
        ligneCommandeService.create(createLigne(p2, c1, 14));
        ligneCommandeService.create(createLigne(p3, c1, 5));

        // Commande 2
        ligneCommandeService.create(createLigne(p1, c2, 10));




        System.out.println("\n--- 1. Liste des produits par catégorie (Catégorie 1: PC) ---");
        List<Produit> produitsCat1 = produitService.findProduitsByCategorie(cat1);
        produitsCat1.forEach(p -> System.out.println("  - " + p.getReference()));

        System.out.println("\n--- 2. Produits commandés entre le 01/01/2013 et 30/04/2013 ---");
        Date d1 = sdf.parse("2013-01-01");
        Date d2 = sdf.parse("2013-04-30");
        List<Produit> produitsEntreDates = produitService.findProduitsCommandesEntreDates(d1, d2);
        produitsEntreDates.forEach(p -> System.out.println("  - " + p.getReference()));


        System.out.println("\n--- 3. Afficher les produits d'une commande (Commande 1) ---");

        afficherDetailsCommande(c1.getId());

        System.out.println("\n--- 4. Produits dont le prix est supérieur à 100 DH (Requête Nommée) ---");
        List<Produit> produitsSup100 = produitService.findProduitsPrixSup100();
        produitsSup100.forEach(p -> System.out.println("  - " + p.getReference() + " (" + p.getPrix() + " DH)"));

    }

    // Méthode utilitaire pour créer une LigneCommande
    private LigneCommandeProduit createLigne(Produit p, Commande c, int quantite) {
        LigneCommandeProduitPK pk = new LigneCommandeProduitPK();
        pk.setProduitId(p.getId());
        pk.setCommandeId(c.getId());

        LigneCommandeProduit lcp = new LigneCommandeProduit();
        lcp.setPk(pk);
        lcp.setProduit(p);
        lcp.setCommande(c);
        lcp.setQuantite(quantite);
        return lcp;
    }

    // Méthode pour l'affichage de l'exercice 3
    private void afficherDetailsCommande(int commandeId) {
        Commande c = commandeService.findById(commandeId);
        if (c == null) {
            System.out.println("Commande non trouvée.");
            return;
        }

        List<LigneCommandeProduit> lignes = ligneCommandeService.findByCommande(c);

        // Formatage de la date comme dans l'exemple "14 Mars 2013"
        SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMMM yyyy");

        System.out.println("Commande : " + c.getId() + "     Date : " + displayFormat.format(c.getDate()));
        System.out.println("Liste des produits :");
        System.out.println("Référence\tPrix\tQuantité");

        for (LigneCommandeProduit ligne : lignes) {
            System.out.println(
                    ligne.getProduit().getReference() + "\t\t" +
                            ligne.getProduit().getPrix() + " DH\t" +
                            ligne.getQuantite()
            );
        }
    }
}