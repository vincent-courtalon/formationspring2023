package com.formation.exercice3.metier;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class Etudiant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)     private int id;
    private String nom;
    private String email;

    @ManyToMany(mappedBy = "etudiants")                         private Set<Cours> courses;
}
