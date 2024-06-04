package com.indocyber.Winterhold;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class WinterholdApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext= SpringApplication.run(WinterholdApplication.class, args);
	 String[] beanNames = applicationContext.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}

}
