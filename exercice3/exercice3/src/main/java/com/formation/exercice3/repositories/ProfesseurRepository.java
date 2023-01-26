package com.formation.exercice3.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formation.exercice3.metier.Professeur;

@Repository
public interface ProfesseurRepository extends CrudRepository<Professeur, Integer>{
    
}
