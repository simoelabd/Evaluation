package ma.projet.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@NamedQuery(name = "Produit.findPrixSup", query = "SELECT p FROM Produit p WHERE p.prix > :prixLimit")

@Entity
@Data
@NoArgsConstructor
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String reference;
    private float prix;

    @ManyToOne
    private Categorie categorie;

    @OneToMany(mappedBy = "produit")
    private List<LigneCommandeProduit> lignesCommandes;
}