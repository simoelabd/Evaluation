package ma.projet.repositories;

import ma.projet.entities.Projet;
import ma.projet.entities.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface TacheRepository extends JpaRepository<Tache, Integer> {


    List<Tache> findByProjet(Projet projet);


    List<Tache> findByPrixSup(@Param("prixLimit") double prixLimit);


    @Query("SELECT DISTINCT t FROM Tache t JOIN t.employesTaches et WHERE et.dateFinReelle BETWEEN :dateDebut AND :dateFin")
    List<Tache> findTachesRealiseesEntreDates(@Param("dateDebut") Date dateDebut, @Param("dateFin") Date dateFin);
}