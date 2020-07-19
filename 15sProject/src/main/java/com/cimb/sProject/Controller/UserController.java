package com.cimb.sProject.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cimb.sProject.Dao.UserRepo;
import com.cimb.sProject.entity.User;
import com.cimb.sProject.util.EmailUtil;



@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
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
		String verifyToken = pwEncoder.encode(user.getUserName()+ user.getEmailAddress());
		
		user.setPassword(encodedPassword);
		user.setVerivied(false);;
		// Simpan verifyToken di database
		user.setVerifyToken(verifyToken);
		user.setRole("user");
		
		User savedUser = userRepo.save(user);
		savedUser.setPassword(null);
		
		// Kirim verifyToken si user ke emailnya user
		String linkToVerify = "http://localhost:8080/user/verify/" + user.getUserName() + "?token=" + verifyToken;
		
		String message = "<h1>Selamat! Registrasi Berhasil</h1>\n";
		message += "Akun dengan username " + user.getUserName() + " telah terdaftar!\n";
		message += "Klik <a href=\"" + linkToVerify + "\">link ini</a> untuk verifikasi email anda.";
		
		
		emailUtil.sendEmail(user.getEmailAddress(), "Registrasi Akun", message);
		
		return savedUser;
	}
	
	@GetMapping("/verify/{userName}")
	public String verifyUserEmail (@PathVariable String userName, @RequestParam String token) {
		User findUser = userRepo.findByUserName(userName).get();
		
		if (findUser.getVerifyToken().equals(token)) {
			findUser.setVerivied(true);;
		} else {
			throw new RuntimeException("Token is invalid");
		}
		
		userRepo.save(findUser);
		
		return "Sukses!";
	}
	
	@GetMapping("/login")
	public User getLoginUser(@RequestParam String userName, @RequestParam String password) {
		User findUser = userRepo.findByUserName(userName).get();

		if (pwEncoder.matches(password, findUser.getPassword())) {	
			findUser.setPassword(null);
			return findUser;
		} 

		throw new RuntimeException("Wrong password!");
	}

}
