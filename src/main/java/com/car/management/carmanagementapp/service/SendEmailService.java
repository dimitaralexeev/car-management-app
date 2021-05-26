package com.car.management.carmanagementapp.service;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Bean
	public JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername("carmanagertest2@gmail.com");
		mailSender.setPassword("xhpawroovtdaylye");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");
		return mailSender;
	}

	public void sendEmail(String to, String body, String topic) {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("carmanagertest2@gmail.com");
		message.setTo(to);
		message.setSubject(topic);
		message.setText(body);

		javaMailSender.send(message);
	}

}
