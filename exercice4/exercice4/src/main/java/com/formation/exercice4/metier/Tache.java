package com.formation.exercice4.metier;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class Tache {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String libelle;
    private int priorite;
    private boolean termine;
    @ManyToOne  private Todolist todolist;
}
