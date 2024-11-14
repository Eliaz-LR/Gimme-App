package com.webtp.gimme.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ModelAndView handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.setStatus(HttpStatus.CONFLICT);
        return modelAndView;
    }
}
