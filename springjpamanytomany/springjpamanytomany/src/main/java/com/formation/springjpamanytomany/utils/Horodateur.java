package com.formation.springjpamanytomany.utils;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Horodateur {
    
    // cette méthode sera appelée avant l'insertion d'une entité utilisant cet entityListener
    // l'entité en question est passé en argument à la méthode
    @PrePersist
    public void avantInsertion(Object entite) {
        log.info("horodatage avant insertion");
        if (entite instanceof IHorodateur) {
            IHorodateur obj = (IHorodateur) entite;
            LocalDateTime ldt = LocalDateTime.now();
            obj.setDateCreation(ldt);
            obj.setDateEdition(ldt);
        }
    }

    @PreUpdate
    public void avantModification(Object entite) {
        log.info("horodatage avant update");
        if (entite instanceof IHorodateur) {
            IHorodateur obj = (IHorodateur) entite;
            LocalDateTime ldt = LocalDateTime.now();
            obj.setDateEdition(ldt);
        }
    }


}
