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
public class Professeur {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)         private int id;
    private String nom;
    private String email;

    @OneToMany(mappedBy = "professeur")                             private Set<Cours> courses;
}
