package ma.projet.service;

import ma.projet.entities.Employe;
import ma.projet.entities.EmployeTache;
import ma.projet.entities.Projet;
import ma.projet.repositories.EmployeRepository;
import ma.projet.repositories.EmployeTacheRepository;
import ma.projet.repositories.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeService {

    @Autowired
    private EmployeRepository employeRepository;
    @Autowired
    private EmployeTacheRepository employeTacheRepository;
    @Autowired
    private ProjetRepository projetRepository;

    public Employe create(Employe o) { return employeRepository.save(o); }
    public Employe findById(int id) { return employeRepository.findById(id).orElse(null); }
    public List<Employe> findAll() { return employeRepository.findAll(); }


    public List<EmployeTache> findTachesRealiseesByEmploye(Employe employe) {
        return employeTacheRepository.findByEmploye(employe);
    }


    public List<Projet> findProjetsGeresByEmploye(Employe employe) {
        return projetRepository.findByChefDeProjet(employe);
    }
}