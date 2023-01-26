package com.formation.exercice4.metier;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class Utilisateur {
    @Id
    private String nom;
    private String password;
    private boolean enabled;
    
}
