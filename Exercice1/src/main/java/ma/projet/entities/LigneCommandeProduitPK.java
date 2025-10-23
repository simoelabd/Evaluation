package ma.projet.entities;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class LigneCommandeProduitPK implements Serializable {
    private int produitId;
    private int commandeId;
}