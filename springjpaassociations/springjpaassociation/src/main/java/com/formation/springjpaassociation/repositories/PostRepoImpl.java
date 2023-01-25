package com.formation.springjpaassociation.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formation.springjpaassociation.metier.Post;

@Service
public class PostRepoImpl implements PostRepo{

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<Post> findAll() {
       return em.createQuery("SELECT p FROM Post p", Post.class)
                .getResultList();
    }

    @Override
    @Transactional
    public Post findById(int id) {
        return em.find(Post.class, id);
    }

    @Override
    @Transactional
    public Post save(Post p) {
        if (p.getId() == 0) {
            em.persist(p);
            return p;    
        }
        else {
            return em.merge(p);
        }
    }
    
}
