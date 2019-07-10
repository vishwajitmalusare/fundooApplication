package com.bridgelabz.fundoonotes.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.ResponseToken;
import com.bridgelabz.fundoonotes.service.UserService;

@RequestMapping("/user")
@RestController
public class UserController {
	@Autowired
	UserService userService;

	@PostMapping("/userRegister")
	public ResponseEntity<Response> register(@RequestBody UserDTO userDto)
			throws UserException, UnsupportedEncodingException {

		Response response = userService.onRegister(userDto);
		System.out .println(response);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/userLogin")
	public ResponseEntity<ResponseToken> onLogin(@RequestBody LoginDTO loginDTO)
			throws UserException, UnsupportedEncodingException {

		ResponseToken response = userService.onLogin(loginDTO);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/validate/{token}")
	public ResponseEntity<Response> emailValidation(@PathVariable String token) throws UserException {

		Response response = userService.validateEmailId(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@GetMapping("/forgotPassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam String emailId)
			throws UnsupportedEncodingException, UserException, MessagingException {
		System.out.println(emailId);
		Response status = userService.forgetPassword(emailId);

		return new ResponseEntity<Response>(status, HttpStatus.OK);

	}

	@PutMapping(value = "/resetPassword/{token}")
	public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam("password") String password)
			throws UserException {
		Response response = userService.resetPaswords(token, password);
		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}
	/*
	 * @PostMapping("/uploadImage")
	public ResponseEntity<Response> uploadImage(@RequestHeader String token , @RequestParam MultipartFile imageFile) throws IOException
	{
		Response response = userService.uploadImage(token, imageFile);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
		
	}
	
	@GetMapping("/getuploadedimage/{token}")
	public ResponseEntity<Resource> getProfilePic(@PathVariable String token) {
		Resource resourseStatus = userService.getUploadedImageOfUser(token);
		return new ResponseEntity<Resource> (resourseStatus, HttpStatus.OK);
}
	 * */
}