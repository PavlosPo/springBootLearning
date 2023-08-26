package com.chapter3.learning.services;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary    /** so the Spring Framework will wire this service instead of the other {@link GreetingServiceImpl) class/instance */
@Service
public class GreetingServicePrimary implements GreetingService{

    @Override
    public String sayGreeting() {
        return "Hello From The Primary Bean";
    }
}
