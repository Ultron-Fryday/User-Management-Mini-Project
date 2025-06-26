package com.omdev.um.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    
	private final JavaMailSender javaMailSender;
	
	public boolean sendEmail(String subject, String body, String to) {
		try {
			log.info("sendEmail called -- to:{}",to);
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setSubject(subject);
			helper.setText(body);
			helper.setTo(to);
			
			javaMailSender.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		log.info("Mail Sent Successfully");
		return true;
	}
}
