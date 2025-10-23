package ma.projet.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class EmployeTache {

    @EmbeddedId
    private EmployeTachePK pk;

    @ManyToOne
    @MapsId("employeId")
    private Employe employe;

    @ManyToOne
    @MapsId("tacheId")
    private Tache tache;

    @Temporal(TemporalType.DATE)
    private Date dateDebutReelle;

    @Temporal(TemporalType.DATE)
    private Date dateFinReelle;
}