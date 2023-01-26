package com.formation.exercice4.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formation.exercice4.metier.Utilisateur;

@Repository
public interface UserRepository extends CrudRepository<Utilisateur, String> {
    
    
}
