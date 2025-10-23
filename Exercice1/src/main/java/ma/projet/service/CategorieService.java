package ma.projet.service;

import ma.projet.entities.Categorie;
import ma.projet.repositories.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    public Categorie create(Categorie o) {
        return categorieRepository.save(o);
    }

    public Categorie findById(int id) {
        return categorieRepository.findById(id).orElse(null);
    }

    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    public void delete(Categorie o) {
        categorieRepository.delete(o);
    }
}