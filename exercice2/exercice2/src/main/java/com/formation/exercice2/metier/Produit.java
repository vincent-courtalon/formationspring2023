package com.formation.exercice2.metier;

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
public class Produit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)         private int id;
                                                                    private String label;
                                                                    private double prix;
                                                                    private double poids;

    @ManyToOne                                                      private Categorie categorie;

}
