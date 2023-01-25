package com.formation.springjpa;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import com.formation.springjpa.metier.Produit;
import com.formation.springjpa.repositories.ProduitRepo;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Configuration
@Slf4j
public class SpringjpaApplication implements CommandLineRunner{

	@Autowired
	private ProduitRepo produitRepo;

	public static void main(String[] args) {
		SpringApplication.run(SpringjpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner input = new Scanner(System.in);
		log.info("demarage");
		List<Produit> produits = produitRepo.findAll();
		produits.forEach(p -> {
			log.info("produit: {}", p.toString());
		});
/* 		Produit p = new Produit(0, "canape sky vert", 1499.99, 3);
		p = produitRepo.createProduit(p);
		log.info("nouveau produit: {} ", p.toString());
		System.out.println("quel article modifier (id)? ");
		int id = Integer.parseInt(input.nextLine());
		System.out.println("nouveau prix ?");
		double prix = Double.parseDouble(input.nextLine());
		Produit p = produitRepo.findById(id);
		if (p != null) {
			p.setPrix(prix);
			p = produitRepo.saveProduit(p);
			log.info("produit mis a jour: {} ", p.toString());
		}
		else {
			log.info("produit inconnu");
		}
 		*/
		 System.out.println("quel article supprimer (id)? ");
		 int id = Integer.parseInt(input.nextLine());
		 if (produitRepo.deleteProduit(id)) {
			log.info("produit supprime");
		 }
		 else {
			log.info("produit inconnu");
		 }
	}

}
