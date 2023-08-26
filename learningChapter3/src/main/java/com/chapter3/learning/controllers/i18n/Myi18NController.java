package com.chapter3.learning.i18n;

import com.chapter3.learning.SERVICES.GreetingService;
import org.springframework.stereotype.Controller;

@Controller
public class Myi18NController {

    private GreetingService greetingService;

    public String sayHello() {
        return greetingService.sayGreeting();
    }

}
