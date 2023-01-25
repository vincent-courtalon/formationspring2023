package com.formation.springjpaassociation.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formation.springjpaassociation.metier.Auteur;
import com.formation.springjpaassociation.metier.Post;

@Service
public class AuteurRepoImpl implements AuteurRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<Auteur> findAll() {
        return em.createQuery("SELECT a FROM Auteur a", Auteur.class)
                 .getResultList();
    }

    @Override
    @Transactional
    public Auteur findById(int id, boolean withPosts) {
        if (withPosts) {
            // LEFT JOIN FETCH permet de forcer le chargement d'une collection associée à l'objet requeté 
            TypedQuery<Auteur> q = em.createQuery(
                "SELECT a FROM Auteur a LEFT JOIN FETCH a.posts WHERE a.id=:pid",
                Auteur.class);
            // pour passer des parametres nommés à une requete JPQL
            q.setParameter("pid", id);
            return q.getSingleResult();
        }
        else
            return em.find(Auteur.class, id);
    }

    @Override
    @Transactional
    public Auteur save(Auteur a) {
        // je gere directement dans le repository les deux cas possible: m.a.j ou creation
        if (a.getId() == 0){
            em.persist(a);
            return a;
        }
        else {
            return em.merge(a);
        }
    }
    
}
