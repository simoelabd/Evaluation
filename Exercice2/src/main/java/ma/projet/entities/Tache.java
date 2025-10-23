package ma.projet.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;


@NamedQuery(name = "Tache.findByPrixSup", query = "SELECT t FROM Tache t WHERE t.prix > :prixLimit")

@Entity
@Data
@NoArgsConstructor
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    private double prix;


    @ManyToOne
    private Projet projet;


    @OneToMany(mappedBy = "tache")
    private List<EmployeTache> employesTaches;
}