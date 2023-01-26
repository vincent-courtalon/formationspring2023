package com.formation.webrest.metier;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class Contact {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)     private int id;
    @Column(length = 100)                                       private String nom;
    @Column(length = 200)                                       private String email;
                                                                private int pointsFidelite;

}
