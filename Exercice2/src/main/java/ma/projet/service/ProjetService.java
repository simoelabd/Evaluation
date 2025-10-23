package ma.projet.service;

import ma.projet.entities.EmployeTache;
import ma.projet.entities.Projet;
import ma.projet.entities.Tache;
import ma.projet.repositories.EmployeTacheRepository;
import ma.projet.repositories.ProjetRepository;
import ma.projet.repositories.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ProjetService {

    @Autowired
    private ProjetRepository projetRepository;
    @Autowired
    private TacheRepository tacheRepository;
    @Autowired
    private EmployeTacheRepository employeTacheRepository;

    public Projet create(Projet o) { return projetRepository.save(o); }
    public Projet findById(int id) { return projetRepository.findById(id).orElse(null); }
    public List<Projet> findAll() { return projetRepository.findAll(); }


    public List<Tache> findTachesPlanifieesByProjet(Projet projet) {
        return tacheRepository.findByProjet(projet);
    }


    public void afficherDetailsTachesRealisees(Projet projet) {
        List<EmployeTache> tachesRealisees = employeTacheRepository.findByTache_Projet(projet);

        SimpleDateFormat sdfProjet = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat sdfTache = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Projet : " + projet.getId() + "     Nom : " + projet.getNom() +
                "     Date début : " + sdfProjet.format(projet.getDateDebut()));
        System.out.println("Liste des tâches:");
        System.out.println("Num\tNom\t\tDate Début Réelle\tDate Fin Réelle");

        for (EmployeTache et : tachesRealisees) {
            Tache t = et.getTache();
            System.out.println(
                    t.getId() + "\t" +
                            t.getNom() + "\t\t" +
                            sdfTache.format(et.getDateDebutReelle()) + "\t\t" +
                            sdfTache.format(et.getDateFinReelle())
            );
        }
    }
}