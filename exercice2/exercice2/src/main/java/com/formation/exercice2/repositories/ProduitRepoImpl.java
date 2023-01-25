package com.formation.exercice2.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formation.exercice2.metier.Produit;

@Service
public class ProduitRepoImpl implements ProduitRepo{

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<Produit> findAll() {
        return em.createQuery("SELECT c FROM Produit c", Produit.class)
        .getResultList();
    }

    @Override
    @Transactional
    public Produit findById(int id) {
        return em.find(Produit.class, id);
    }

    @Override
    @Transactional
    public List<Produit> findByPrixLesserThan(double prix) {
        TypedQuery<Produit> q = em.createQuery(
                "SELECT p FROM Produit p  WHERE p.prix < :maxprix",
                Produit.class);
            // pour passer des parametres nommés à une requete JPQL
        q.setParameter("maxprix", prix);
        return q.getResultList();
    }

    @Override
    @Transactional
    public Produit save(Produit p) {
        if (p.getId() == 0){
            em.persist(p);
            return p;
        }
        else {
            return em.merge(p);
        }
    }
    
}
