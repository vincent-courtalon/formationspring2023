package com.formation.springjpa.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.formation.springjpa.metier.Produit;

@Service
public class ProduitRepoImpl implements ProduitRepo{

    // cette anotation indique à spring d'injecter içi
    // un entityManager quand une des fonctions du service en à besoin
    @PersistenceContext
    private EntityManager em;

    // l'annotation transactional à de multiples roles içi
    // 1) prépare l'entity manager avant l'appel de la fonction
    // 2) ouvre une transaction
    // 3) la fonction est exécutée
    // 4) la transaction est fermée et l'entity manager correctement nettoyé
    @Override
    @Transactional
    public List<Produit> findAll() {
        // attention, il ne s'agit pas içi de SQL , mais d'un langage intermédiaire
        // HQL,JPQL, EJBQL -> requeter les entité
        // içi, Produit dénote l'objet/entité Produit, pas la table sql
        return em.createQuery("SELECT p from Produit p", Produit.class)
                  .getResultList();
    }

    @Override
    @Transactional
    public Produit createProduit(Produit p) {
        // persist permet d'insérer une ligne dans la base
        // c'est la première sauvegarde d'une entité après sa création dans le code
        em.persist(p);
        return p;
    }

    @Override
    @Transactional
    public Produit findById(int id) {
        return em.find(Produit.class, id);
    }

    @Override
    @Transactional
    public Produit saveProduit(Produit p) {
        // avec un entityManager, il n'existe en même temps au maximum qu'un seul objet
        // correspondant à une ligne particulière de la table
        // 2 cas:
        // s'il y a déjà un objet en mémoire correspondant à la ligne
        // il le met à jour ainsi que la base
        // sinon,
        // il met à jour dans la base et renvoie votre objet initial (celui fournit en parametre)
        return em.merge(p);
    }

    @Override
    @Transactional
    public boolean deleteProduit(int id) {
        Produit p = em.find(Produit.class, id);
        if (p != null) {
            em.remove(p);
            return true;
        }
        return false;
    }
    
}
