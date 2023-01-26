package com.formation.webrest.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formation.webrest.metier.Article;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Integer>{
    
}
