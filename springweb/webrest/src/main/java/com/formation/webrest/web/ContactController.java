package com.formation.webrest.web;

import java.util.Collections;
import java.util.Optional;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.formation.webrest.metier.Contact;
import com.formation.webrest.repositories.ContactRepository;
import com.formation.webrest.utils.ApiValidationErrors;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/contact")
@Validated
@Slf4j
public class ContactController {
    

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ApiValidationErrors> handleConstraintViolationException(ConstraintViolationException ex) {
        final ApiValidationErrors errors = ApiValidationErrors.fromConstraintViolations(ex.getConstraintViolations());
        log.warn("requete bloquée pour cause d'erreur sur champs {}", errors);
        return new ResponseEntity<ApiValidationErrors>(errors, HttpStatus.BAD_REQUEST);
    }

    @Autowired
    private ContactRepository contactRepository;



    @RequestMapping(path = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> creerContact(
        @RequestParam(name="nom", required = true)
        @Size(min=2, max= 100)
        String nom,

        @RequestParam(name="email", required = true)
        @Pattern(regexp = "[-a-zA-Z0-9.]+@[-a-zA-Z0-9.]+", message = "email non valide")
        String email,

        @RequestParam(name="pointsFidelite", required = true)
        @Min(value = 0, message = "les points de fidelité ne peuvent être négatifs")
        @Max(value = 100, message = "un contact ne peut avoir plus de 100 points de fidélité")
        int pointsFidelite
    ) {
        return new ResponseEntity<Object>(contactRepository.save(new Contact(0, nom, email, pointsFidelite)), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Page<Contact>> listeContacts(
        @PageableDefault(page = 0, size = 5) Pageable page,
        @RequestParam(name="searchTerm", required = false)
        Optional<@Size(max = 50, message = "recherche sur 50 caracteres au maximum" )String> searchTerm) {

        
        return searchTerm
            .map(st -> new ResponseEntity<Page<Contact>>(
                    contactRepository.findAllByNomContainingIgnoreCase(st, page), HttpStatus.OK))
            .orElseGet( () -> new ResponseEntity<Page<Contact>>(
                    contactRepository.findAll(page), HttpStatus.OK));
    }


    @RequestMapping(path = "/{id:[0-9]+}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> modifierContact(
        @PathVariable(name="id")
        int id,

        @RequestParam(name="nom", required = false)
        Optional<@Size(min=2, max= 100) String> nom,

        @RequestParam(name="email", required = false)
        Optional<@Pattern(regexp = "[-a-zA-Z0-9.]+@[-a-zA-Z0-9.]+", message = "email non valide")String> email,

        @RequestParam(name="pointsFidelite", required = false)
        Optional<@Min(value = 0, message = "les points de fidelité ne peuvent être négatifs")
                @Max(value = 100, message = "un contact ne peut avoir plus de 100 points de fidélité")Integer> pointsFidelite
    ) {
        Optional<Contact> oc =  contactRepository.findById(id);
        if (oc.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Contact c = oc.get();
        c.setNom(nom.orElse(c.getNom()));
        c.setEmail(email.orElse(c.getEmail()));
        c.setPointsFidelite(pointsFidelite.orElse(c.getPointsFidelite()));
        return new ResponseEntity<Object>(contactRepository.save(c), HttpStatus.OK);
    }


    @RequestMapping(path = "/resetPoints", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> creerContact(
        @RequestParam(name="pointsFidelite", required = true)
        @Min(value = 0, message = "les points de fidelité ne peuvent être négatifs")
        @Max(value = 100, message = "un contact ne peut avoir plus de 100 points de fidélité")
        int pointsFidelite
    ) {

        return new ResponseEntity<Object>(
            Collections.singletonMap("nbUpdated", contactRepository.reinitialisePointsFidelite(pointsFidelite)), 
            HttpStatus.OK);
    }

}
