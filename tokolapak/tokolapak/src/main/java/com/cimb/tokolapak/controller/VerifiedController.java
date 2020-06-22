package com.cimb.tokolapak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.dao.UserRepo;
import com.cimb.tokolapak.entity.User;

@RestController
public class VerifiedController {

	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/userVerified")
		public User VerifikasiUser (@RequestParam String username) {
		
		// Cari username yang sesuai 
		User findUser = userRepo.findByUsername(username).get();
		if(findUser!=null) {
			findUser.setVerifiedUser(true);
			userRepo.save(findUser);
			return(findUser);
		} else {
			return null;
		}
	}
	
}
