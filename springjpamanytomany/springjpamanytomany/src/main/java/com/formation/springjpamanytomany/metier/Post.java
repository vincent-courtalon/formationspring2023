package com.formation.springjpamanytomany.metier;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.formation.springjpamanytomany.utils.Horodateur;
import com.formation.springjpamanytomany.utils.IHorodateur;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor 
@Entity
@EntityListeners({Horodateur.class}) // c'est içi qu'on "plug" notre horodateur automatique
public class Post implements IHorodateur {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)     private int id;
                                                                private String titre;
                                                                private String corps;
                                                                private LocalDateTime dateCreation;
                                                                private LocalDateTime dateEdition;
    // le ManyToMany sans mappedBy est le coté "maitre" de l'association
    @JoinTable(
        name = "etiquetage",
        joinColumns = @JoinColumn(name = "clefpost"),
        inverseJoinColumns = @JoinColumn(name="cleftag")
    )                                                            
    @ManyToMany(/*fetch = FetchType.EAGER*/)                        private Set<Tag> tags;

    public void addTag(Tag t) {
        // attention, si l'objet n'as pour provenance l'entitymanager
        // c.a.d qu'il a été instancié par nous (avant d'etre persisté par exemple)
        // la collection tags peut être a null
        if (tags == null) {
            this.tags = new HashSet<>();
        }
        // si on veut être encore plus carré, comparer les identifiant plutôt que les instances comme içi
        if (!tags.contains(t)) {
            tags.add(t);
        }
    }

    public Post(int id, String titre, String corps) {
        this.id = id;
        this.titre = titre;
        this.corps = corps;
    }

   

}
