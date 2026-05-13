package com.rogersamson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rogersamson.service.EmailService;

@SpringBootApplication
public class EmailSnippetsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailSnippetsApplication.class, args);
		EmailService emailService = new EmailService();
		emailService.sendEmail();
	}

}
