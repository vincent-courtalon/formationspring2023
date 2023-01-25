package com.formation.exercice2.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formation.exercice2.metier.Categorie;

@Service
public class CategorieRepoImpl implements CategorieRepo{

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<Categorie> findAll() {
        return em.createQuery("SELECT c FROM Categorie c", Categorie.class)
                  .getResultList();
    }

    @Override
    @Transactional
    public Categorie findById(int id, boolean withProduits) {
        if (withProduits) {
            // LEFT JOIN FETCH permet de forcer le chargement d'une collection associée à l'objet requeté 
            TypedQuery<Categorie> q = em.createQuery(
                "SELECT c FROM Categorie c LEFT JOIN FETCH c.produits WHERE c.id=:cid",
                Categorie.class);
            // pour passer des parametres nommés à une requete JPQL
            q.setParameter("cid", id);
            return q.getSingleResult();
        }
        else
            return em.find(Categorie.class, id);
    }

    @Override
    @Transactional
    public Categorie save(Categorie c) {
        if (c.getId() == 0){
            em.persist(c);
            return c;
        }
        else {
            return em.merge(c);
        }
    }
    
}
