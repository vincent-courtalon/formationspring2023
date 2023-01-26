package com.formation.springjpamanytomany.repositories;

import java.util.List;

import com.formation.springjpamanytomany.metier.Post;
import com.formation.springjpamanytomany.metier.dto.PostStat;

public interface PostRepo {
    List<Post> findAll();
    Post findById(int id, boolean withTags);
    Post save(Post p);
    List<PostStat> statsPosts();
}
