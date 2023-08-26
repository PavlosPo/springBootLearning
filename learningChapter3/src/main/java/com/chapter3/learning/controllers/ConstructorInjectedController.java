package com.chapter3.learning.controllers;

import com.chapter3.learning.services.GreetingService;
import org.springframework.stereotype.Controller;

@Controller
public class ConstructorInjectedController {
    private final GreetingService greetingService;

    // public ConstructorInjectedController(@Qualifier("greetingServiceImpl")GreetingService greetingService) {
    // will use the other implementation of GreetingService interface, instead of the primary
    public ConstructorInjectedController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public String sayHello() {
        return greetingService.sayGreeting();
    }
}
