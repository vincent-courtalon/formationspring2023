package com.formation.exercice4.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.formation.exercice4.metier.Todolist;

@Repository
public interface TodolistRepository extends PagingAndSortingRepository<Todolist, Integer> {
    
}
