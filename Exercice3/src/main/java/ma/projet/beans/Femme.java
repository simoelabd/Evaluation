package ma.projet.beans;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Femme extends Personne {

    @OneToMany(mappedBy = "femme")
    private List<Mariage> mariages;
}