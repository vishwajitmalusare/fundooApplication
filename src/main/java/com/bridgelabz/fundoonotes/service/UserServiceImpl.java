package com.bridgelabz.fundoonotes.service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.model.Email;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.ResponseToken;
import com.bridgelabz.fundoonotes.utility.EmailService;
import com.bridgelabz.fundoonotes.utility.ResponseHelper;
import com.bridgelabz.fundoonotes.utility.TokenUtil;

@PropertySource("classpath:message.properties")
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
	private EmailService mailServise;
	
	@Autowired
	private Response statusResponse;
 
	@Autowired
	private Environment environment;

	@Override
	public Response onRegister(UserDTO userDto) {

		String emailid = userDto.getEmailId();
		Email email =new Email();
		Optional<User> useralreadyPresent = userRepository.findByEmailId(emailid);
		
		if (useralreadyPresent.isPresent()) {
			throw new UserException(environment.getProperty("status.Register.emailExistError"));
		}
		else {
		User user = modelMapper.map(userDto, User.class);

		String password = passwordEncoder.encode(userDto.getPassword());

		user.setPassword(password);
		user = userRepository.save(user);
	
		email.setTo(userDto.getEmailId());
		email.setSubject("Email Verification ");
		System.out.println(user.getUserId());
		try {
			email.setBody( mailServise.getLink("http://localhost:8081/user/validate/",user.getUserId()));
		} catch (IllegalArgumentException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		mailServise.send(email);

		statusResponse = ResponseHelper.statusResponse(200, "register successfully");
		return statusResponse;

	}
	}
	@Override
	public ResponseToken onLogin(LoginDTO loginDto) throws UserException, UnsupportedEncodingException {
		Optional<User> user = userRepository.findByEmailId(loginDto.getEmailId());
		ResponseToken response = new ResponseToken();
		if (user.isPresent()) {

			return authentication(user, loginDto.getPassword());

		}

		System.out.println("response is " + response);
		return response;

	}

	@Override
	public ResponseToken authentication(Optional<User> user, String password) {

		ResponseToken response = new ResponseToken();
		if (user.get().isVerify()) {
			boolean status = passwordEncoder.matches(password, user.get().getPassword());

			if (status == true) {
				String token = tokenUtil.createToken(user.get().getUserId());
				response.setToken(token);
				response.setStatusCode(200);
				response.setStatusMessage(environment.getProperty("user.userLogin"));
				return response;
			}

			throw new UserException(401, environment.getProperty("user.userLogin.password"));
		}

		throw new UserException(401, environment.getProperty("user.userLogin.verification"));
	}

	@Override
	public Response validateEmailId(String token) {
		Long id = tokenUtil.decodeToken(token);
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserException(404, environment.getProperty("user.validation.email")));
		user.setVerify(true);
		userRepository.save(user);
		statusResponse = ResponseHelper.statusResponse(200, environment.getProperty("user.validation"));
		return statusResponse;
	}

	@Override
	public Response forgetPassword(String emailId) throws UserException, UnsupportedEncodingException {
		Optional<User> useralreadyPresent = userRepository.findByEmailId(emailId);

		if (!useralreadyPresent.isPresent()) {

			throw new UserException(401, environment.getProperty("user.forgetPassword.emaiId"));
		}
		Long id = useralreadyPresent.get().getUserId();
	
		Email emailObj = new Email();
		emailObj .setTo(emailId);
		emailObj.setSubject("Forgot Password ....");
		try {
			emailObj.setBody( mailServise.getLink("http://localhost:8081/user/resetPassword/",id));
		} catch (IllegalArgumentException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		mailServise.send(emailObj);
		return ResponseHelper.statusResponse(200, environment.getProperty("user.forgetPassword.link"));
	}

	@Override
	public Response resetPaswords(String token, String password) {
		Long id = tokenUtil.decodeToken(token);
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserException(404, environment.getProperty("user.resetPassword.user")));
		String encodedpassword = passwordEncoder.encode(password);
		user.setPassword(encodedpassword);
		userRepository.save(user);
		return ResponseHelper.statusResponse(200, "password successfully reset");

	}

}
