package com.formation.springjpamanytomany;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import com.formation.springjpamanytomany.metier.Post;
import com.formation.springjpamanytomany.metier.Tag;
import com.formation.springjpamanytomany.metier.dto.PostStat;
import com.formation.springjpamanytomany.repositories.PostRepo;
import com.formation.springjpamanytomany.repositories.TagRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Configuration
@Slf4j
public class SpringjpamanytomanyApplication implements CommandLineRunner {


	@Autowired
	private PostRepo postRepo;

	@Autowired
	private TagRepository tagRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringjpamanytomanyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner reader = new Scanner(System.in);
		log.info("démarrage");
		test1();
		reader.nextLine();

		
		log.info("test2");
		test2(reader);
		reader.nextLine();
 
		log.info("test3");
		test3();
		reader.nextLine();

		log.info("fini");

	}

	private void test1() {
		Tag[] tags = new Tag[5];
		tags[0] = new Tag(0, "voyage", null);
		tags[1] = new Tag(0, "cuisine", null);
		tags[2] = new Tag(0, "java", null);
		tags[3] = new Tag(0, "jardinage", null);
		tags[4] = new Tag(0, "cinema", null);
		
		for (int i = 0; i < tags.length; i++) {
			tags[i] = tagRepository.save(tags[i]);
		}

		// je générer 100 posts
		// chaque post a une chance sur deux d'etre étiqueté par chaque tag
		Random rd = new Random();
		for (int i = 1; i<= 100; i++) {
			Post p = new Post(0,"post " + i, "blah blah");
			for (int j = 0; j < tags.length; j++) {
				if (rd.nextBoolean()) {
					p.addTag(tags[j]);
				}
			}
			postRepo.save(p);
		}
		
	}

	private void test2(Scanner reader) {
		Post p = postRepo.findById(3, true);
		log.info("post : {} tags: {}", 
				p.getTitre(), 
				p.getTags().stream()
							.map(t -> t.getLibelle()).collect(Collectors.joining(",")));
		System.out.println("id du tag a ajouter?");
		int tid = Integer.parseInt(reader.nextLine());
		p.addTag(tagRepository.findById(tid).get());
		p.setCorps("un nouveau texte dans le post");
		// içi comme on ajoute dans la collection des tags du Post
		// hibernate insere une ligne dans la table de jointure
		postRepo.save(p);
		log.info("stats posts");
		List<PostStat> stats = postRepo.statsPosts();
		stats.forEach(st -> System.out.println(st));
	}

	public void test3() {
		List<Tag> tags = tagRepository.findByLibelleContaining("ag");
		tags.forEach(t -> System.out.println(t.getLibelle()));
	}

}
