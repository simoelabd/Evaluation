package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.repositories.FemmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FemmeService {

    @Autowired
    private FemmeRepository femmeRepository;


    @PersistenceContext
    private EntityManager em;

    public Femme create(Femme o) { return femmeRepository.save(o); }
    public Femme findById(int id) { return femmeRepository.findById(id).orElse(null); }
    public List<Femme> findAll() { return femmeRepository.findAll(); }


    public Femme findFemmeLaPlusAgee() {
        return femmeRepository.findFirstByOrderByDateNaissanceAsc();
    }


    public long getNombreEnfantsEntreDates(int femmeId, Date date1, Date date2) {
        return femmeRepository.countEnfantsEntreDates(femmeId, date1, date2);
    }


    public List<Femme> findFemmesMarieesAuMoinsDeuxFois() {
        return femmeRepository.findFemmesMarieesAuMoinsDeuxFois();
    }


    public long countHommesMariesNFoisEntreDates(int n, Date date1, Date date2) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Homme> homme = cq.from(Homme.class);



        Subquery<Long> subquery = cq.subquery(Long.class);
        Root<Mariage> mariage = subquery.from(Mariage.class);
        subquery.select(cb.count(mariage));

        Predicate hommePredicate = cb.equal(mariage.get("homme"), homme);
        Predicate datePredicate = cb.between(mariage.get("dateDebut"), date1, date2);

        subquery.where(cb.and(hommePredicate, datePredicate));

        cq.select(cb.count(homme));
        cq.where(cb.equal(subquery, (long)n));

        return em.createQuery(cq).getSingleResult();
    }
}