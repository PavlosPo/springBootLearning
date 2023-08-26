package com.chapter3.learning.controllers;

import com.chapter3.learning.services.GreetingService;
import com.chapter3.learning.services.GreetingServiceImpl;
import org.springframework.stereotype.Controller;


@Controller
public class MyController {


    private final GreetingService greetingService;

    public MyController() {
        this.greetingService = new GreetingServiceImpl();
    }

    public String sayHello() {
        System.out.println("I am in the controller");

        return greetingService.sayGreeting();
    }
}
