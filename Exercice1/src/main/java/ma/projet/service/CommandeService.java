package ma.projet.service;

import ma.projet.entities.Commande;
import ma.projet.repositories.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    public Commande create(Commande o) {
        return commandeRepository.save(o);
    }

    public Commande findById(int id) {
        return commandeRepository.findById(id).orElse(null);
    }

    public List<Commande> findAll() {
        return commandeRepository.findAll();
    }
}