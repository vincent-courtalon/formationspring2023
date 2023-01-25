package com.formation.exercice2.repositories;

import java.util.List;

import com.formation.exercice2.metier.Categorie;

public interface CategorieRepo {
    List<Categorie> findAll();
    Categorie findById(int id, boolean withProduits);
    Categorie save(Categorie c);
    
}
