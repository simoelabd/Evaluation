package ma.projet.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class LigneCommandeProduit {

    @EmbeddedId
    private LigneCommandeProduitPK pk;

    @ManyToOne
    @MapsId("produitId")
    private Produit produit;

    @ManyToOne
    @MapsId("commandeId")
    private Commande commande;

    private int quantite;
}