package com.rogersamson.service;

import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class EmailService {

	public void sendEmail() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername("rmsamson0818@gmail.com");
		mailSender.setPassword("GerLan0818");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("rmsamson0818@gmail.com");
		message.setTo("rogelio.samson@gmail.com");
		message.setSubject("EMail Sender Test");
		message.setText("This is a test for Email Sender Snippet");
		mailSender.send(message);


	}

}
