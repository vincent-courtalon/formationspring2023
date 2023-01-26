package com.formation.exercice3.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.formation.exercice3.metier.Etudiant;
import com.formation.exercice3.metier.dto.CoursStat;

@Repository
public interface EtudiantRepository extends CrudRepository<Etudiant, Integer>{
    
    List<Etudiant> findAllByCoursesId(int cid);

    // vous pouvez écrire la requete vous même
    // et faire correspondre les parametres dans l'ordre indiqué
    // ATTENTION, cette technique ne fonctionne que sur des entité retournées ou des types simple
    // pour renvoyer autre chose (ou requete native), cela nécéssite des "mapper" a définir en plus
    @Query("SELECT distinct(et) from Etudiant et left join et.courses as c WHERE c.dateDebut > ?1")
    List<Etudiant> findparticipantCoursAfter(LocalDate debut);

    @Query("SELECT distinct(et) from Etudiant et left join et.courses as c WHERE c.professeur.id = ?1")
    List<Etudiant> findparticipantCoursProf(int professeurId);

   
}
