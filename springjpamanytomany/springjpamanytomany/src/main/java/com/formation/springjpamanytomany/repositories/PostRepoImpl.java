package com.formation.springjpamanytomany.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formation.springjpamanytomany.metier.Post;
import com.formation.springjpamanytomany.metier.dto.PostStat;

@Service
public class PostRepoImpl implements PostRepo{

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<Post> findAll() {
       return em.createQuery("select p FROM Post p", Post.class)
                .getResultList();
    }

    @Override
    @Transactional
    public Post findById(int id, boolean withTags) {
        if (withTags) {
            TypedQuery<Post> q = em.createQuery("SELECT p FROM Post p LEFT JOIN FETCH p.tags WHERE p.id=:id", Post.class);
            q.setParameter("id", id);
            return q.getSingleResult();
        }
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

    @Override
    @Transactional
    public List<PostStat> statsPosts() {
       Query q = em.createQuery(
        "SELECT p.id, p.titre, count(t.id) FROM Post p LEFT JOIN p.tags as t group by p.id, p.titre");
        List<Object[]> stats = q.getResultList();
        List<PostStat> pstats = stats.stream().map(row -> new PostStat((int)row[0], (String)row[1], (long)row[2]))
                                             .toList();
        return pstats;
    }
    
}
