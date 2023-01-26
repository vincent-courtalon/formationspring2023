package com.formation.exercice4.metier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class Todolist {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(min=5, max=100) // ces annotations sont reprises entre autre par springdoc
    private String titre;

    private LocalDateTime dateCreation;

    // cette annotation indique à la librairie jackson ( java <-> json)
    // de ne pas tenir compte de cette propriété
    @JsonIgnore
    @OneToMany(mappedBy = "todolist", cascade = CascadeType.REMOVE)
    private Set<Tache> taches;
}
