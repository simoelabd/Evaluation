package ma.projet.repositories;

import ma.projet.beans.Femme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface FemmeRepository extends JpaRepository<Femme, Integer> {

    Femme findFirstByOrderByDateNaissanceAsc();


    @Query(value = "SELECT COALESCE(SUM(m.nbr_enfant), 0) FROM Mariage m " +
            "WHERE m.femme_id = :femmeId AND m.date_debut BETWEEN :date1 AND :date2",
            nativeQuery = true)
    long countEnfantsEntreDates(@Param("femmeId") int femmeId, @Param("date1") Date date1, @Param("date2") Date date2);


    @Query("SELECT f FROM Femme f WHERE (SELECT COUNT(m) FROM Mariage m WHERE m.femme = f) >= 2")
    List<Femme> findFemmesMarieesAuMoinsDeuxFois();
}