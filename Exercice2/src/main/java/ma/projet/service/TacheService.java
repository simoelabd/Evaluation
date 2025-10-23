package ma.projet.service;

import ma.projet.entities.Tache;
import ma.projet.repositories.TacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class TacheService {

    @Autowired
    private TacheRepository tacheRepository;

    public Tache create(Tache o) { return tacheRepository.save(o); }
    public Tache findById(int id) { return tacheRepository.findById(id).orElse(null); }
    public List<Tache> findAll() { return tacheRepository.findAll(); }


    public List<Tache> findTachesPrixSup1000() {
        return tacheRepository.findByPrixSup(1000.0);
    }

    public List<Tache> findTachesRealiseesEntreDates(Date dateDebut, Date dateFin) {
        return tacheRepository.findTachesRealiseesEntreDates(dateDebut, dateFin);
    }
}