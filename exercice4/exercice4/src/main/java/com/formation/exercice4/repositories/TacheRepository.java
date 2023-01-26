package com.formation.exercice4.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.formation.exercice4.metier.Tache;

@Repository
public interface TacheRepository extends PagingAndSortingRepository<Tache, Integer> {
    

    Page<Tache> findAllByTodolistId(int todolistId, Pageable page);

}
