package com.project.diploma.web.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ModelAndView exceptionHandler(Throwable exception){
        ModelAndView model = new ModelAndView("error");
        model.addObject(exception.getMessage());
        return model;
    }
}
