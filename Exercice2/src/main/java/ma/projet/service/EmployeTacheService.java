package ma.projet.service;

import ma.projet.entities.EmployeTache;
import ma.projet.repositories.EmployeTacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeTacheService {

    @Autowired
    private EmployeTacheRepository employeTacheRepository;

    public EmployeTache create(EmployeTache o) {
        return employeTacheRepository.save(o);
    }
}