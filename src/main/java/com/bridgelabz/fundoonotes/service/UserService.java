package com.bridgelabz.fundoonotes.service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.ResponseToken;

public interface UserService {

	Response onRegister(UserDTO userDto) throws UserException, UnsupportedEncodingException;

	ResponseToken onLogin(LoginDTO loginDto) throws UserException, UnsupportedEncodingException;

	ResponseToken authentication(Optional<User> user, String password);

	Response validateEmailId(String token);

	Response resetPaswords(String token, String password);

	Response forgetPassword(String emailId) throws UserException, UnsupportedEncodingException;
	
}
