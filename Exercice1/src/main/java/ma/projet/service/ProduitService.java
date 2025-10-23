package ma.projet.service;

import ma.projet.entities.Categorie;
import ma.projet.entities.Produit;
import ma.projet.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    public Produit create(Produit o) {
        return produitRepository.save(o);
    }

    public Produit findById(int id) {
        return produitRepository.findById(id).orElse(null);
    }

    public List<Produit> findAll() {
        return produitRepository.findAll();
    }

    // 1. Afficher la liste des produits par catégorie
    public List<Produit> findProduitsByCategorie(Categorie categorie) {
        return produitRepository.findByCategorie(categorie);
    }

    // 2. Afficher les produits commandés entre deux dates
    public List<Produit> findProduitsCommandesEntreDates(Date dateDebut, Date dateFin) {
        return produitRepository.findProduitsCommandesEntreDates(dateDebut, dateFin);
    }

    // 3. (Géré par LigneCommandeService et CommandeService, voir test)

    // 4. Afficher la liste des produits dont le prix est supérieur à 100 DH
    public List<Produit> findProduitsPrixSup100() {
        return produitRepository.findPrixSup(100.0f);
    }
}