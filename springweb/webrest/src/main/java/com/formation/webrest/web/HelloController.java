package com.formation.webrest.web;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/hello")
@Slf4j
public class HelloController {
    
    @RequestMapping(path = "/salutation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody // cette annotation indique que la méthode renvoie directement une donnée
    // spring boot MVC configure automatique une librairie qui s'apelle jackson
    // c'st la librairie qui se charge de convertir un objet java en json, et inversement
    public Map<String, String> salutation() {
        log.info("dans salutation de HelloController");
        return Collections.singletonMap("message", "bonjour le " + LocalDateTime.now());
    }

    @RequestMapping(path = "/salutation2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> salutation2(@RequestParam(name="nom", defaultValue = "bob") String nom) {
        log.info("dans salutation2 de HelloController avec nom= {}", nom);
        return Collections.singletonMap("message", "bonjour " + nom);
    }

    // pathvariable permet d'extraire sous forme de parametre une partie de l'url
    @RequestMapping(path = "/salutation3/{nom}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> salutation3(@PathVariable(value="nom") String nom) {
        log.info("dans salutation3 de HelloController avec nom= {}", nom);
        return Collections.singletonMap("message", "bonjour " + nom);
    }

}
