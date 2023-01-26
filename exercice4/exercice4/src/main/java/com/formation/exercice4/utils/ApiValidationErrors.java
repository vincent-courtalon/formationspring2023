package com.formation.exercice4.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ApiValidationErrors {
    private List<ApiValidationError> errors;

    public void addError(String property, Object value, String message) {
        this.errors.add(new ApiValidationError(property, value, message));
    }

    // remplir notre objet de gestion d'erreur à partir des contraintes renvoyée par sprng boot validation
    public void fillFromConstraintViolations(Set<ConstraintViolation<?>> constraintViolations) {
        constraintViolations.forEach(cv -> {
            addError(cv.getPropertyPath().toString(), cv.getInvalidValue(), cv.getMessage());
        });
    }

    public static ApiValidationErrors fromConstraintViolations(Set<ConstraintViolation<?>> constraintViolations) {
        final ApiValidationErrors ave = new ApiValidationErrors();
        ave.fillFromConstraintViolations(constraintViolations);
        return ave;
    }

    public ApiValidationErrors() {
        errors = new ArrayList<ApiValidationError>();
    }

    @Override
    public String toString() {
        return errors.stream().map(err -> err.getProperty()).collect(Collectors.joining(","));
    }
}
