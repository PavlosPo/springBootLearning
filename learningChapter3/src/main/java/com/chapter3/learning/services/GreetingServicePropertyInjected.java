package com.chapter3.learning.services;

import org.springframework.stereotype.Service;

@Service
public class GreetingServicePropertyInjected implements GreetingService{

    @Override
    public String sayGreeting() {
        return "Firnds don't let friends to property injection!!!!";
    }
}
