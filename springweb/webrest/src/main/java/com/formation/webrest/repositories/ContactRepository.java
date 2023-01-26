package com.formation.webrest.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.formation.webrest.metier.Contact;

@Repository
public interface ContactRepository extends PagingAndSortingRepository<Contact, Integer>{
    
    Page<Contact> findAllByNomContainingIgnoreCase(String searchTerm, Pageable page);


    // on a aussi possibilité d'inclure des requetes de type update/insert/delete
    // attention cependant a spécifier le @modifying (pour rafraichir les entité si nécssaire)
    // et transactional
    @Query(nativeQuery = true, value = "UPDATE contact set points_fidelite=?1")
    @Modifying(clearAutomatically = true)
    @Transactional
    int reinitialisePointsFidelite(int newValue);
}

