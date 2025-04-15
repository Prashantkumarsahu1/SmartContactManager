package com.prashant.SmartContactManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
 //@ComponentScan(basePackages = "com.prashant.SmartContactManager.helper")
// @SpringBootApplication(scanBasePackages = {"com.example", "com.other.helper"})
public class SmartContactManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartContactManagerApplication.class, args);
		// System.out.println("Hello");
	}
}
