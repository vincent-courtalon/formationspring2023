package com.formation.springjpaassociation.repositories;

import java.util.List;

import com.formation.springjpaassociation.metier.Post;

public interface PostRepo {
    List<Post> findAll();
    Post findById(int id);
    Post save(Post p);
}
