package com.formation.exercice4.web;

import java.time.LocalDateTime;
import java.util.Collections;

import javax.validation.ConstraintViolationException;
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

import com.formation.exercice4.metier.Todolist;
import com.formation.exercice4.repositories.TodolistRepository;
import com.formation.exercice4.utils.ApiValidationErrors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/todolist")
@Validated
@Slf4j
public class TodolistController {
    
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ApiValidationErrors> handleConstraintViolationException(ConstraintViolationException ex) {
        final ApiValidationErrors errors = ApiValidationErrors.fromConstraintViolations(ex.getConstraintViolations());
        log.warn("requete bloquée pour cause d'erreur sur champs {}", errors);
        return new ResponseEntity<ApiValidationErrors>(errors, HttpStatus.BAD_REQUEST);
    }

    @Autowired
    private TodolistRepository todolistRepository;

    @Operation(summary = "creation d'une nouvelle todoliste")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "201", 
                     description = "la todoliste crée", 
                     content= @Content(schema=@Schema(implementation= Todolist.class))),
        @ApiResponse(responseCode = "400", 
                     description = "parametre de création invalides", 
                     content= @Content(schema=@Schema(implementation= ApiValidationErrors.class)))

    })
    @RequestMapping(path = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Todolist> createTodoList(
        @RequestParam( name="titre", required = true)
        @Parameter(name = "titre", description = "le titre de la todoliste", example = "ma todo liste", required = true)
        @Size(min = 5, max=100, message = "le titre doit faire entre 5 et 100 caracteres") String titre) {
            return new ResponseEntity<Todolist>(
                todolistRepository.save(new Todolist(0, titre, LocalDateTime.now(), null)), HttpStatus.CREATED);   
    }




    @RequestMapping(path = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Page<Todolist>> listeTodoList(
            @PageableDefault(page = 0, size = 10) Pageable page) {
            return new ResponseEntity<Page<Todolist>>(
                todolistRepository.findAll(page), HttpStatus.OK);   
    }

    @RequestMapping(path = "/{id:[0-9]+}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> deletetodolist(
                            @PathVariable(name="id") int id) {
        if (todolistRepository.existsById(id)){
            todolistRepository.deleteById(id);
            return new ResponseEntity<Object>(Collections.singletonMap("nbDeleted", 1), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
