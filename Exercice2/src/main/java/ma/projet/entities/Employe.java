package ma.projet.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private String telephone;


    @OneToMany(mappedBy = "chefDeProjet")
    private List<Projet> projetsGeres;


    @OneToMany(mappedBy = "employe")
    private List<EmployeTache> tachesRealisees;
}