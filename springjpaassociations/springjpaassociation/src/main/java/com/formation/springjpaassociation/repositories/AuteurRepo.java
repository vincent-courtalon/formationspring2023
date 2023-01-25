package com.formation.springjpaassociation.repositories;

import java.util.List;

import com.formation.springjpaassociation.metier.Auteur;

public interface AuteurRepo {
    List<Auteur> findAll();
    Auteur findById(int id, boolean withPosts);
    Auteur save(Auteur a);    
}
