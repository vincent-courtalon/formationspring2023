package com.formation.springjpa.repositories;

import java.util.List;

import com.formation.springjpa.metier.Produit;

public interface ProduitRepo {
    
    List<Produit> findAll();
    Produit createProduit(Produit p);
    Produit findById(int id);
    Produit saveProduit(Produit p);
    boolean deleteProduit(int id);
}
