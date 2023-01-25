package com.formation.springjpaassociation.metier;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)     private int id;
    @Column(length = 100, nullable = false)                     private String titre;
    @Column(length = 500)                                       private String corps;
                                                                private LocalDateTime dateCreation;
    // plusieurs posts sont reliés à un auteur: N - 1 : many to one
    // la clef étrangere référenant l'auteur du post sera stokée dans cette table
    // c'est donc le coté "maitre" de l'association
    @ManyToOne(/*fetch = FetchType.LAZY, cascade = CascadeType.PERSIST*/)    private Auteur auteur;
    
}
