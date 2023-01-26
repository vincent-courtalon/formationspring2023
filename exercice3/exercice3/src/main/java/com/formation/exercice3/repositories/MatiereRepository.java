package com.formation.exercice3.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formation.exercice3.metier.Matiere;

@Repository
public interface MatiereRepository extends CrudRepository<Matiere, Integer>{
    
}
