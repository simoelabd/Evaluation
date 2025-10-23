package ma.projet.repositories;

import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface MariageRepository extends JpaRepository<Mariage, Integer> {

    List<Mariage> findByHommeAndDateDebutBetween(Homme homme, Date dateDebut, Date dateFin);

    List<Mariage> findByHomme(Homme homme);
}