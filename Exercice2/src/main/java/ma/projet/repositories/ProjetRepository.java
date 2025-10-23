package ma.projet.repositories;

import ma.projet.entities.Employe;
import ma.projet.entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Integer> {


    List<Projet> findByChefDeProjet(Employe chefDeProjet);
}