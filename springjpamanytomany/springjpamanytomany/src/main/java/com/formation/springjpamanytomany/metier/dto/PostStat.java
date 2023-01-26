package com.formation.springjpamanytomany.metier.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class PostStat {
   private int id;
   private String titre;
   private long nbTags; 
}
