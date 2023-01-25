package com.formation.exercice2;

import java.util.HashSet;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import com.formation.exercice2.metier.Categorie;
import com.formation.exercice2.metier.Produit;
import com.formation.exercice2.repositories.CategorieRepo;
import com.formation.exercice2.repositories.ProduitRepo;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@Configuration
public class Exercice2Application implements CommandLineRunner{

	@Autowired
	private CategorieRepo categorieRepo;

	@Autowired
	private ProduitRepo produitRepo;

	public static void main(String[] args) {
		SpringApplication.run(Exercice2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Scanner reader = new Scanner(System.in);
		Categorie c1 = new Categorie(0, "outillage", new HashSet<>());
		Categorie c2 = new Categorie(0, "decoration", new HashSet<>());
		
		c1 = categorieRepo.save(c1);
		c2 = categorieRepo.save(c2);

		Produit p1 = new Produit(0, "perceuse", 79.99, 2.8, c1);
		Produit p2 = new Produit(0, "ponceuse", 59.99, 3.8, c1);
		Produit p3 = new Produit(0, "mirroir sdb", 129.99, 7.8, c2);

		produitRepo.save(p1);
		produitRepo.save(p2);
		produitRepo.save(p3);
		
		reader.nextLine();

		produitRepo.findByPrixLesserThan(80)
					.forEach(p -> log.info("{} -  {}", p.getLabel(), p.getCategorie().getNom()));

	}

}
