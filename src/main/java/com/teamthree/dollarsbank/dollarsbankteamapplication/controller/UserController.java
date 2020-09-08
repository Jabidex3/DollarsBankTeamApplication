package com.teamthree.dollarsbank.dollarsbankteamapplication.controller;

import java.net.http.HttpResponse;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.teamthree.dollarsbank.dollarsbankteamapplication.model.User;
import com.teamthree.dollarsbank.dollarsbankteamapplication.service.UserService;

import net.minidev.json.JSONObject;

// Allows http requests from other servers
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@RestController
public class UserController
{
	
	@Autowired
	UserService userService;
	
	
	//@ResponseBody
	@PostMapping("/registerUser")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user)
	{
		
		System.out.println(user.toString());
		if(userService.userExists(user.getEmail()))
		{
			// returns a Conflict if user already exists
			return new ResponseEntity<User>(HttpStatus.CONFLICT);
		}
		else
		{
			
			System.out.println(user.toString());
			// Adding user to database from RequestBody ( JSON body )
			userService.addUser(user);
			// returns Created
			return new ResponseEntity<User>(HttpStatus.CREATED);
		}
		
	}
	
	//@ResponseBody
	@GetMapping("/loginUser/{email}/{password}")
	public ResponseEntity<User> loginUser(@Valid @PathVariable String email,@Valid @PathVariable String password)
	{
		
		// TODO:: setup httpSession for user when logged in
		// Currently set to take in email and password in the path, maybe set to RequestBody later if Front end wants
		//HttpHeaders responseHeaders = new HttpHeaders();
		User user = userService.findUserByEmail(email);
		if(user == null)
		{
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		else if (user.getPassword().equals(password))
		{
			// returns user and accepted in the http body response
			return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		
	}
	
	//@ResponseBody
	@PostMapping("/editUser")
	public ResponseEntity<User> editUser(@Valid @RequestBody User user)
	{
		// Takes user JSON from front-end and replaces fields in User from database
		// TODO:: Security, maybe sessions or something to prevent people from editing everyones account
		User userFinal = userService.findUserByEmail(user.getEmail());
		userFinal.setFirstName(user.getFirstName());
		userFinal.setLastName(user.getLastName());
		userFinal.setPassword(user.getPassword());
		userService.addUser(userFinal);
		return new ResponseEntity<User>(userFinal,HttpStatus.ACCEPTED);
		
	}
	
	//@ResponseBody
	@PostMapping("/deleteUser")
	public ResponseEntity<User> deleteUser(@Valid @RequestBody User user)
	{
		// deletes User from database
		// TODO:: Security, maybe sessions or something to prevent people from editing everyones account
		
		
		if(userService.findUserByEmail(user.getEmail())== null)
		{
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		else 
		{
			userService.delete(user.getUserId());
			return new ResponseEntity<User>(HttpStatus.ACCEPTED);
		}
		
		
	}
}
