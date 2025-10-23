package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.repositories.HommeRepository;
import ma.projet.repositories.MariageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HommeService {

    @Autowired
    private HommeRepository hommeRepository;

    @Autowired
    private MariageRepository mariageRepository;

    public Homme create(Homme o) { return hommeRepository.save(o); }
    public Homme findById(int id) { return hommeRepository.findById(id).orElse(null); }
    public List<Homme> findAll() { return hommeRepository.findAll(); }


    public List<Femme> findEpousesEntreDates(Homme homme, Date date1, Date date2) {
        List<Mariage> mariages = mariageRepository.findByHommeAndDateDebutBetween(homme, date1, date2);


        return mariages.stream()
                .map(Mariage::getFemme)
                .distinct()
                .collect(Collectors.toList());
    }


    public void afficherDetailsMariages(Homme homme) {
        List<Mariage> mariages = mariageRepository.findByHomme(homme);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("Nom : " + homme.getNom().toUpperCase() + " " + homme.getPrenom());


        System.out.println("Mariages En Cours :");
        int i = 1;
        for (Mariage m : mariages) {
            if (m.getDateFin() == null) {
                System.out.println(
                        i++ + ". Femme : " + m.getFemme().getPrenom() + " " + m.getFemme().getNom().toUpperCase() +
                                "\tDate Début : " + sdf.format(m.getDateDebut()) +
                                "\tNbr Enfants : " + m.getNbrEnfant()
                );
            }
        }


        System.out.println("\nMariages échoués :");
        i = 1;
        for (Mariage m : mariages) {
            if (m.getDateFin() != null) {
                System.out.println(
                        i++ + ". Femme : " + m.getFemme().getPrenom() + " " + m.getFemme().getNom().toUpperCase() +
                                "\tDate Début : " + sdf.format(m.getDateDebut()) +
                                "\tDate Fin : " + sdf.format(m.getDateFin()) +
                                "\tNbr Enfants : " + m.getNbrEnfant()
                );
            }
        }
    }
}