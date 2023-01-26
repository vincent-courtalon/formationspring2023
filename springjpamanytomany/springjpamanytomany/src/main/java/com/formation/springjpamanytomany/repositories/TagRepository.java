package com.formation.springjpamanytomany.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.formation.springjpamanytomany.metier.Tag;

// ceci est un repository automatique de spring data
// Il hérite d'une des interface fournis à cet effet par springData
// en l'occurence, il fournira içi les opérations CRUD de base
// les deux parametres du template sont la classe de l'entité géré par le repository, et la classe de sa clef primaire
@Repository
public interface TagRepository extends CrudRepository<Tag, Integer>{
    
    // vous ouvez définir des "queryMethod" personnalisée dans un repository spring data
    // si la nomenclature du nom de la méthode est respectée, il pourra automatiquement en déduire
    // la requete SQL à executer
    // find,count,etc
    // By<nom du champ capitalisé>
    // si test particulier (comme like) -> Containing
    // Greater,Lesser
    // combiner plusieurs WHERE en enchainant avec
    // AndBy ...
    List<Tag> findByLibelleContaining(String searchTerm);

}
