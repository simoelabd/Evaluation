package ma.projet.repositories;

import ma.projet.entities.Commande;
import ma.projet.entities.LigneCommandeProduit;
import ma.projet.entities.LigneCommandeProduitPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigneCommandeRepository extends JpaRepository<LigneCommandeProduit, LigneCommandeProduitPK> {

    // 3. (Partiel) Lignes de commande pour une commande donnée
    List<LigneCommandeProduit> findByCommande(Commande commande);
}