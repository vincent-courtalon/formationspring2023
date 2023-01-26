package com.formation.exercice3.metier;

import java.util.Set;

import javax.persistence.Entity;
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
public class Matiere {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)     private int id;
    private String libelle;

    @OneToMany(mappedBy = "matiere")                            private Set<Cours> courses;
}
