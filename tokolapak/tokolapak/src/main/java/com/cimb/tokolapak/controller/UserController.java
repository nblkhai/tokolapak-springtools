//package com.cimb.tokolapak.controller;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import com.cimb.tokolapak.dao.UserRepo;
//import com.cimb.tokolapak.entity.User;
//import com.cimb.tokolapak.util.EmailUtil;
//@RestController
//@RequestMapping("/users")
//@CrossOrigin
//public class UserController {
//	// BCryptEncoder : library dari Spring Security
//	// BCrypt bakal ngasih salt (bumbuin) passwordnya, jadi misal ada 2 user punya password sama. encodingnya tidak sama
//	// User1 password 123321 -> abc123abc122
//	// User2 password 123321 -> cba1212ab122
//	
//	@Autowired
//	private UserRepo userRepo;
//	
//	private PasswordEncoder pwEncoder = new BCryptPasswordEncoder();
//	
//	@Autowired
//	private EmailUtil emailUtil;
//	
//	@PostMapping
//	public User registerUser(@RequestBody User user) {
////		Optional<User> findUser = userRepo.findByUsername(user.getUsername());
//		
//		// cek apakah username sudah terdaftar
////		if(findUser.toString() != "Optional Empty") {
////			throw new RuntimeException("username exist!");
////		}
//		
//		// akan menghasilkan password yang sudah di hash
//		String encodedPassword = pwEncoder.encode(user.getPassword());
//		
//		// set password user dengan password yang sudah di encode
//		user.setPassword(encodedPassword);
//		
//		User savedUser = userRepo.save(user);
//		savedUser.setPassword(null);
//		return savedUser;
//	}
//	
//	
//	// cara 1 menggunakan POST Method -> not reccommended
//	@PostMapping("/login")
//	public User loginUser(@RequestBody User user) {
//		User findUser = userRepo.findByUsername(user.getUsername()).get();
//		
//		if(pwEncoder.matches(user.getPassword(), findUser.getPassword())) {
//			findUser.setPassword(null);
//			return findUser;
//		}
//		
//		return null;
//		
//	}
//	
//	// Cara 2 dengan Method GET -> aman reccommended
//	// localhost:8080/users/login?username=seto&password=123
//	@GetMapping("/login")
//	public User getLoginUser(@RequestParam String username, @RequestParam String password) {
//		User findUser = userRepo.findByUsername(username).get();
//		
//		if(pwEncoder.matches(password, findUser.getPassword())) {
//			findUser.setPassword(null);
//			return findUser;
//		}
//		
//		throw new RuntimeException("wrong password");
//	}
//	
//	@PostMapping("/sendEmail")
//	public String sendEmailTesting() {
//		this.emailUtil.sendEmail("bellsskkyy13@gmail.com", "testing mail springboot", "Hello World! \nwazzaap");
//		return "Email sent!";
//	}
//}

package com.cimb.tokolapak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.dao.UserRepo;
import com.cimb.tokolapak.entity.User;
import com.cimb.tokolapak.util.EmailUtil;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
	
	// User1 Password = 123321 -> abc123abc1212
	// User2 Password = 123321 -> cba1212ab123
	
	@Autowired
	private UserRepo userRepo;
	
	private PasswordEncoder pwEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private EmailUtil emailUtil;
	
	@PostMapping
	public User registerUser(@RequestBody User user) {
//		Optional<User> findUser = userRepo.findByUsername(user.getUsername());
//		
//		if (findUser.toString() != "Optional.empty") {
//			throw new RuntimeException("Username exists!");
//		}
		String encodedPassword = pwEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		User savedUser = userRepo.save(user);
		savedUser.setPassword(null);
		
		return savedUser;
	}
	
	// Cara 1 menggunakan POST method
	@PostMapping("/login")
	public User loginUser (@RequestBody User user) {
		User findUser = userRepo.findByUsername(user.getUsername()).get();
								// Password raw       password sudah encode
		if (pwEncoder.matches(user.getPassword(), findUser.getPassword())) {
			findUser.setPassword(null);
			return findUser;
		} 
		
		throw new RuntimeException("Wrong password!");
//		return null;
	}
	// localhost:8080/users/login?username=seto&password=password123
	@GetMapping("/login")
	public User getLoginUser(@RequestParam String username, @RequestParam String password) {
		User findUser = userRepo.findByUsername(username).get();

		if (pwEncoder.matches(password, findUser.getPassword())) {	
			findUser.setPassword(null);
			return findUser;
		} 

		throw new RuntimeException("Wrong password!");
	}
	
	@PostMapping("/sendEmail")
	public String sendEmailTesting() {
		this.emailUtil.sendEmail("bellsskkyy13@gmail.com", "Testing Spring Mail", "<h1>Hey there</h1> \nApa Kabar?");
		return "Email Sent!";
	}
	
	// TUGAS 22-JUNI-2020
	
	@PostMapping("/registration")
	public User userRegistration (@RequestBody User user) {
		
		String encodedPassword = pwEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		
		User savedUser = userRepo.save(user);
		savedUser.setPassword(null);
		
		emailUtil.sendEmail(user.getEmail(), "Registration", "<h1>Congratulation, "+ user.getUsername()+ "! Your Account Has Been Registered</h1> Please confirm your email address.\n "
				+ "<a href=\"http://localhost:8080/userVerified?username="+ user.getUsername() + "\">Click Here </a>");
		
		return savedUser;
		
	}
}