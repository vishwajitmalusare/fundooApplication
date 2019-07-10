package com.bridgelabz.fundoonotes.utility;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.model.Email;
/**
 * Email service for email sending using JMS
 * */
@Component
@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private TokenUtil userToken;
public void send(Email email) {
	SimpleMailMessage message = new SimpleMailMessage();
	message.setTo(email.getTo());
	message.setSubject(email.getSubject());
	message.setText(email.getBody());
	
	System.out.println("Sending Email ");
	
	javaMailSender.send(message);
	
	System.out.println("Emailsend Successfully");
}
/*
/**
	 * Purpose : Method to generate validation link
	 * @param link : Passing the link of user 
	 * @param id : Passing the user id 
	 * @return : Return validation link 
	 * @throws IllegalArgumentException
	 * @throws UnsupportedEncodingException * */
public  String getLink(String link, long id) throws IllegalArgumentException,UnsupportedEncodingException
{
	return link+userToken.createToken(id);
	}
}
 