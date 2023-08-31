package springframework.spring6restmvc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@ControllerAdvice
public class CustomErrorController {  // a controller for the validators, they are returning info as a bad response with body.


    @ExceptionHandler
    ResponseEntity handleJPAViolations(TransactionSystemException exception) {  // this will handle JPA
        return ResponseEntity.badRequest().build();
    }



    /**
     * It will handle any {@link MethodArgumentNotValidException) thrown if any validation in JavaBEANS will fail to validate.
     * We must include the @Validate in the methods needed to validate the passed Java Beans.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindErrors(MethodArgumentNotValidException exception) {

        List errorList = exception
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return errorMap;
                })
                .collect(Collectors.toList());
        return  ResponseEntity.badRequest().body(errorList);
    }
}
