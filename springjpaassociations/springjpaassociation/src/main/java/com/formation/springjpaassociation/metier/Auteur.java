package com.formation.springjpaassociation.metier;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class Auteur {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)     private int id;
                                                                private String nom;
                                                                private String prenom;
                                                                private String email;
    // içi, OneToMany est juste le mirroir du manytoone coté Post
    // c'est un attribut pour accès plus facile, mais sans effet direct sur la base
    // car c'est le coté Post qui est "maitre", c.a.d la table avec la clef étrangere
    // par défaut, les posts ne sont pas chargé au requetage d'un auteur, cela peut
    // etre changé via l'attribut FetchType.EAGER
    @OneToMany(mappedBy = "auteur"/* , fetch = FetchType.EAGER*/)    private Set<Post> posts;
}
