package ma.projet.service;

import ma.projet.beans.Mariage;
import ma.projet.repositories.MariageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MariageService {

    @Autowired
    private MariageRepository mariageRepository;

    public Mariage create(Mariage o) { return mariageRepository.save(o); }
}