package com.formation.exercice2.repositories;

import java.util.List;

import com.formation.exercice2.metier.Produit;

public interface ProduitRepo {
    List<Produit> findAll();
    Produit findById(int id);
    List<Produit> findByPrixLesserThan(double prix);
    Produit save(Produit p);
    
}
