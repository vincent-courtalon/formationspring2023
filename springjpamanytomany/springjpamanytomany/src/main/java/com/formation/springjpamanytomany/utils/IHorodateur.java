package com.formation.springjpamanytomany.utils;

import java.time.LocalDateTime;

public interface IHorodateur {
    void setDateCreation(LocalDateTime dateCreation);
    void setDateEdition(LocalDateTime dateModification);
    
}
