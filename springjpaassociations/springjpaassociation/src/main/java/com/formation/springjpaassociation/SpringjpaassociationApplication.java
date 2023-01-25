package com.formation.springjpaassociation;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import com.formation.springjpaassociation.metier.Auteur;
import com.formation.springjpaassociation.metier.Post;
import com.formation.springjpaassociation.repositories.AuteurRepo;
import com.formation.springjpaassociation.repositories.PostRepo;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Configuration
@Slf4j
public class SpringjpaassociationApplication implements CommandLineRunner {

	@Autowired
	private AuteurRepo auteurRepo;

	@Autowired
	private PostRepo postRepo;

	public static void main(String[] args) {
		SpringApplication.run(SpringjpaassociationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner reader = new Scanner(System.in);
		log.info("demarrage");

		reader.nextLine();
		test1();
		log.info("test1 termine");

		reader.nextLine();
		test2();
		log.info("test2 termine");

		reader.nextLine();
		test3();
		log.info("test3 termine");

		reader.nextLine();
		log.info("fin des essais");

	}

	private void test1() {
		Auteur a1 = new Auteur(0, "Zola", "Emile", "emile.zola@aol.com", null);
		Auteur a2 = new Auteur(0, "Vernes", "Jules", "j.vernes@nasa.gov", null);


		auteurRepo.save(a1);
		auteurRepo.save(a2);
		
		Post p1 = new Post(0, "la bete humaine", "blah blah...", LocalDateTime.now(), a1);
		Post p2 = new Post(0, "l'assomoir", "blah blah...", LocalDateTime.now(), a1);
	
		Post p3 = new Post(0, "de la terre à la lune", "blah blah...", LocalDateTime.now(), a2);
	
		postRepo.save(p1);
		postRepo.save(p2);
		postRepo.save(p3);
		
	}

	private void test2() {
		// je demande a charger un post
		Post p1 = postRepo.findById(1);
		// j'affiche des informations sur celui-ci et son auteur
		log.info("titre post: '{}' , nom auteur '{}'", p1.getTitre(), p1.getAuteur().getNom());

		Auteur a2 = auteurRepo.findById(2, true);
		a2.getPosts().forEach(p -> log.info("post '{}'", p.getTitre()));
	}

	private void test3() {
		Auteur a3 = new Auteur(0, "Hugo", "Victor", "vhugo@caramail.com", new HashSet<>());
		a3 = auteurRepo.save(a3);
		Post p = new Post(0, "les misérables", "blah blah", LocalDateTime.now(), a3);
		postRepo.save(p);


		Post p2 = new Post(0, "notre dame", "balh blah", LocalDateTime.now(), null);
		p2 = postRepo.save(p2);
		// cette ligne la ne met pas à jour la base!!!!
		a3.getPosts().add(p2);
		auteurRepo.save(a3);
	}

}
