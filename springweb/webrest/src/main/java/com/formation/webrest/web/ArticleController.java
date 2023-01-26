package com.formation.webrest.web;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.formation.webrest.metier.Article;
import com.formation.webrest.repositories.ArticleRepository;

@Controller
@RequestMapping("/article")
public class ArticleController {
    
    @Autowired
    private ArticleRepository articleRepository;


    @RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> creerArticle(@RequestParam(name= "titre",required = true) String titre, 
                                @RequestParam(name= "corps",required = true)String corps) {
        Article a = new Article(0, titre, corps);
        return new ResponseEntity<Object>(articleRepository.save(a), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{id:[0-9]+}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> detailsArticle(@PathVariable(name = "id") int id) {
        return articleRepository.findById(id).map(a -> new ResponseEntity<Object>(a, HttpStatus.OK))
                                             .orElse(new ResponseEntity<Object>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> listeArticles() {
        return new ResponseEntity<Object>(articleRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(path = "/{id:[0-9]+}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Object> effacerArticle(@PathVariable(name = "id") int id) {
        if (!articleRepository.existsById(id)) {
           return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }
        articleRepository.deleteById(id);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }


    @RequestMapping(path = "/{id:[0-9]+}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Object> creerArticle(
                                @PathVariable(name = "id") int id,
                                @RequestParam(name= "titre",required = false) Optional<String> titre, 
                                @RequestParam(name= "corps",required = false) Optional<String> corps) {
        Optional<Article> a = articleRepository.findById(id);
        if (a.isEmpty()) {
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }
        Article toUpdate = a.get();
        toUpdate.setTitre(titre.orElse(toUpdate.getTitre()));
        toUpdate.setCorps(corps.orElse(toUpdate.getCorps()));
        return new ResponseEntity<Object>(articleRepository.save(toUpdate), HttpStatus.OK);
    }

}
