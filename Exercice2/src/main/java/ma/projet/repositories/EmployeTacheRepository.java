package ma.projet.repositories;

import ma.projet.entities.Employe;
import ma.projet.entities.EmployeTache;
import ma.projet.entities.EmployeTachePK;
import ma.projet.entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmployeTacheRepository extends JpaRepository<EmployeTache, EmployeTachePK> {


    List<EmployeTache> findByEmploye(Employe employe);


    List<EmployeTache> findByTache_Projet(Projet projet);
}