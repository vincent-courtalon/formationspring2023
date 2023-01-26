package com.formation.exercice3.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formation.exercice3.metier.Cours;
import com.formation.exercice3.metier.dto.CoursStat;

@Repository
public interface CoursRepository extends CrudRepository<Cours, Integer>{
    
    @Query(nativeQuery = true, name = "coursstatsRequest")
    List<CoursStat> listeStatsCours();
}
