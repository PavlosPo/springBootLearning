package com.chapter3.learning;

import com.chapter3.learning.controllers.MyController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LearningChapter3Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(LearningChapter3Application.class, args);

		MyController controller = ctx.getBean(MyController.class);

		System.out.println("I am in the Main Method");

		System.out.println(controller.sayHello());
	}



}
