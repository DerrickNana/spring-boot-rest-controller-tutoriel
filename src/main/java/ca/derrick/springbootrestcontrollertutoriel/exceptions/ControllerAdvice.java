package ca.derrick.springbootrestcontrollertutoriel.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public List<String> carNotFound(NoSuchElementException noSuchElementException){
        return Arrays.asList(noSuchElementException.getMessage());
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public List<String> wrongInputPathVariableRequestParam(
            ConstraintViolationException constraintViolationException){
        return constraintViolationException.getConstraintViolations()
                .stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .toList();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public List<String> wrongInputRequestBody(
            MethodArgumentNotValidException methodArgumentNotValidException){
        return  methodArgumentNotValidException.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .toList();
    }
}
