package com.formation.springjpamanytomany.metier;

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
public class Tag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)     private int id;
                                                                private String libelle;
    @ManyToMany(mappedBy = "tags")                              private Set<Post> posts;
}
