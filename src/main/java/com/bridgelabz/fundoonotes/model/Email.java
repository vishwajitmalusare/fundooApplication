package com.bridgelabz.fundoonotes.model;

import org.springframework.stereotype.Component;

@Component
public class Email {
private String from;
private String to;
private String subject;
private String body;
public String getFrom() {
	return from;
}
public void setFrom(String from) {
	this.from = from;
}
public Email(String from, String to, String subject, String body) {
	super();
	this.from = from;
	this.to = to;
	this.subject = subject;
	this.body = body;
}
public Email() {
	super();
}
@Override
public String toString() {
	return "Email [from=" + from + ", to=" + to + ", subject=" + subject + ", body=" + body + "]";
}
public String getTo() {
	return to;
}
public void setTo(String to) {
	this.to = to;
}
public String getSubject() {
	return subject;
}
public void setSubject(String subject) {
	this.subject = subject;
}
public String getBody() {
	return body;
}
public void setBody(String body) {
	this.body = body;
}
}
