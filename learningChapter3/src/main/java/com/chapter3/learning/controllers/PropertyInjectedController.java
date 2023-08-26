package com.chapter3.learning.controllers;

import com.chapter3.learning.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class PropertyInjectedController {

    @Qualifier("greetingServicePropertyInjected")   // will use this service implementation, instead of the primary
    @Autowired      // we want this to be injected
    GreetingService greetingService;

    public String sayHello() {
        return greetingService.sayGreeting();
    }
}
