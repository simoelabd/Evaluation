package ma.projet.repositories;

import ma.projet.entities.Categorie;
import ma.projet.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Integer> {

    List<Produit> findByCategorie(Categorie categorie);

    @Query("SELECT DISTINCT p FROM Produit p JOIN p.lignesCommandes lcp JOIN lcp.commande c WHERE c.date BETWEEN :dateDebut AND :dateFin")
    List<Produit> findProduitsCommandesEntreDates(@Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin);

    List<Produit> findPrixSup(@Param("prixLimit") float prixLimit);
}