package com.formation.springjdbc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.formation.springjdbc.metier.Article;
import com.formation.springjdbc.repo.ArticleDAO;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class SpringjdbcApplication implements CommandLineRunner {

	@Autowired
	private ArticleDAO articleDAO;

	public static void main(String[] args) {
		SpringApplication.run(SpringjdbcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("demarrage");
		List<Article> articles = articleDAO.listeArticles();
		articles.forEach(a -> System.out.println(a));
		log.debug(" {} ligne inseree", articleDAO.creerArticle("canoe " + LocalDateTime.now(), 499.99));
		articles = articleDAO.listeArticles();
		articles.forEach(a -> System.out.println(a));
		System.out.println(articleDAO.modifierArticle(1, Optional.empty(), Optional.of(399.9)) + " lignes modifiées");
		articles = articleDAO.listeArticles();
		articles.forEach(a -> System.out.println(a));
		articleDAO.deleteArticle(5);
	}

}
