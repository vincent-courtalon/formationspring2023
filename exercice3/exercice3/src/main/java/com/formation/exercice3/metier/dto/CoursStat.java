package com.formation.exercice3.metier.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class CoursStat {
    private int id;
    private String titre;
    private long nbEtudiant; 
}
