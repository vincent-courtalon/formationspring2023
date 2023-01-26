package com.formation.exercice3.metier;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;

import com.formation.exercice3.metier.dto.CoursStat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@SqlResultSetMappings(
    {
        @SqlResultSetMapping(name="coursstatsResult",
        classes = {
            @ConstructorResult(
                targetClass = CoursStat.class,
                columns = {
                    @ColumnResult(name="id", type = Integer.class),
                    @ColumnResult(name="titre", type = String.class),
                    @ColumnResult(name="nbetudiant", type = Long.class)
                }
            )
        })
    }
)
@NamedNativeQuery(name="coursstatsRequest",
                 resultSetMapping = "coursstatsResult",
                 resultClass = CoursStat.class,
                 query="SELECT c.id, c.titre, count(DISTINCT(et.id)) as nbetudiant FROM cours as c " +
                 " LEFT JOIN cours_etudiants as ce ON c.id = ce.courses_id " + 
                 " LEFT JOIN etudiant as et ON et.id = ce.etudiants_id " +
                 " GROUP BY c.id, c.titre")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class Cours {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private int id;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String titre;
    private int capacite;

    @ManyToMany                                     private Set<Etudiant> etudiants;
    @ManyToOne                                      private Professeur professeur;
    @ManyToOne                                      private Matiere matiere;

    public void addEtudiant(Etudiant et) {
        if (etudiants == null) {
            this.etudiants = new HashSet<>();
        }
        if (!etudiants.contains(et)) {
            etudiants.add(et);
        }
    }

}
