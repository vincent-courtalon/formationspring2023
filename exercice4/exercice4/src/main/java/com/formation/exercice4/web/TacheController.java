package com.formation.exercice4.web;

import java.util.Optional;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

import com.formation.exercice4.metier.Tache;
import com.formation.exercice4.metier.Todolist;
import com.formation.exercice4.repositories.TacheRepository;
import com.formation.exercice4.repositories.TodolistRepository;
import com.formation.exercice4.utils.ApiValidationErrors;

import lombok.extern.slf4j.Slf4j;

@Controller
@Validated
@Slf4j
@RequestMapping("/tache")
public class TacheController {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ApiValidationErrors> handleConstraintViolationException(ConstraintViolationException ex) {
        final ApiValidationErrors errors = ApiValidationErrors.fromConstraintViolations(ex.getConstraintViolations());
        log.warn("requete bloquée pour cause d'erreur sur champs {}", errors);
        return new ResponseEntity<ApiValidationErrors>(errors, HttpStatus.BAD_REQUEST);
    }

    @Autowired
    private TacheRepository tacheRepository;

    @Autowired
    private TodolistRepository todolistRepository;

    @RequestMapping(path = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Tache> createTache(
        @RequestParam( name="libelle", required = true)
        @Size(min = 5, max=50, message = "le libelle doit faire entre 5 et 100 caracteres") String libelle,

        @RequestParam(name = "priorite", defaultValue = "1")
        @Min(value = 1, message = "la priorité soit etre supérieur à 0")
        @Max(value = 10, message = "la priorité ne peut dépasser 10")
        int priorite,

        @RequestParam(name = "todolisteId", required = true)
        int todolisteId
        ) {
            Optional<Todolist> liste = todolistRepository.findById(todolisteId);
            if (!liste.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Tache>(
                tacheRepository.save(new Tache(0, libelle, priorite, false, liste.get())), HttpStatus.CREATED);   
    }

    
    @RequestMapping(path = "/fromlist/{todolisteId:[0-9]+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Page<Tache>> listeTaches(
        @PathVariable(name = "todolisteId", required = true)
        int todolisteId,
        @PageableDefault(page = 0, size = 10) Pageable page
    ) {
        return new ResponseEntity<Page<Tache>>(tacheRepository.findAllByTodolistId(todolisteId, page), HttpStatus.OK);
    }

}
