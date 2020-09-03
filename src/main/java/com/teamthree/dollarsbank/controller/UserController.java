package com.teamthree.dollarsbank.controller;

import java.net.http.HttpResponse;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.teamthree.dollarsbank.model.User;
import com.teamthree.dollarsbank.service.UserService;

// Allows http requests from other servers
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class UserController
{
	
	@Autowired
	UserService userService;
	
	
	@ResponseBody
	@PostMapping("/registerUser")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user)
	{
		
		System.out.println(user.toString());
		if(userService.userExists(user.getEmail()))
		{
			// returns not accepted if user already exists
			return new ResponseEntity<User>(HttpStatus.NOT_ACCEPTABLE);
		}
		else
		{
			
			System.out.println(user.toString());
			// Creating user from JSON body
			userService.addUser(user);
			// returns accepted
			return new ResponseEntity<User>(HttpStatus.ACCEPTED);
		}
		
	}
}
