package ma.projet.service;

import ma.projet.entities.Commande;
import ma.projet.entities.LigneCommandeProduit;
import ma.projet.repositories.LigneCommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LigneCommandeService {

    @Autowired
    private LigneCommandeRepository ligneCommandeRepository;

    public LigneCommandeProduit create(LigneCommandeProduit o) {
        return ligneCommandeRepository.save(o);
    }

    // Méthode pour l'exercice 3
    public List<LigneCommandeProduit> findByCommande(Commande commande) {
        return ligneCommandeRepository.findByCommande(commande);
    }
}