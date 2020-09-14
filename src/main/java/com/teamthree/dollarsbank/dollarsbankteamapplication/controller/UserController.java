package com.teamthree.dollarsbank.dollarsbankteamapplication.controller;

import java.net.http.HttpResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamthree.dollarsbank.dollarsbankteamapplication.model.User;
import com.teamthree.dollarsbank.dollarsbankteamapplication.service.AccountService;
import com.teamthree.dollarsbank.dollarsbankteamapplication.service.TransactionService;
import com.teamthree.dollarsbank.dollarsbankteamapplication.service.UserService;

import net.minidev.json.JSONObject;

// Allows http requests from other servers
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController
{
	
	@Autowired
	UserService userService;
	@Autowired
	AccountService accountService;
	@Autowired
	TransactionService transService;
	
	//@ResponseBody
	@PostMapping("/register")
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
	@PostMapping("/login")
	public ResponseEntity<User> loginUser(@Valid @RequestBody User user, HttpSession session)
	{
		
		System.out.println(session.getId());
		// TODO:: setup httpSession for user when logged in
		// Currently set to take in email and password in the path, maybe set to RequestBody later if Front end wants
		//HttpHeaders responseHeaders = new HttpHeaders();
		User user2 = userService.findUserByEmail(user.getEmail());
		if(user2 == null)
		{
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		else if (user2.getPassword().equals(user.getPassword()))
		{
			session.setAttribute("email", user2.getEmail());
			session.setAttribute("password", user2.getPassword());
			User userReturn = new User();
			userReturn.setEmail(user2.getEmail());
			userReturn.setFirstName(user2.getFirstName());
			userReturn.setLastName(user2.getLastName());
			userReturn.setCreatedAt(user2.getCreatedAt());
			userReturn.setUpdatedAt(user2.getUpdatedAt());
			userReturn.setUserId(user2.getUserId());
			userReturn.setPassword("");
			// returns user and accepted in the http body response
			return new ResponseEntity<User>(userReturn, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		
	}
	
	//@ResponseBody
	@PutMapping("/user")
	public ResponseEntity<User> editUser(@Valid @RequestBody User user, HttpSession session)
	{
		if(session.getAttribute("email") == null)
		{
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
			
		}
		else
		{
			User userReturn = userService.findUserByEmail(session.getAttribute("email").toString());
			userReturn.setFirstName(user.getFirstName());
			userReturn.setLastName(user.getLastName());
			userReturn.setPassword(user.getPassword());
			userService.addUser(userReturn);
			return new ResponseEntity<User>(userReturn,HttpStatus.ACCEPTED);
		}
		// Takes user JSON from front-end and replaces fields in User from database
		// TODO:: Security, maybe sessions or something to prevent people from editing everyones account
	
		
	}
	
	//@ResponseBody
	@DeleteMapping("/user")
	public ResponseEntity<User> deleteUser(HttpSession session)
	{
		// deletes User from database
		// TODO:: Security, maybe sessions or something to prevent people from editing everyones account
		if(session.getAttribute("email") == null)
		{
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
			
		}
		else
		{
			User user = userService.findUserByEmail(session.getAttribute("email").toString());
			int id = user.getUserId();
			transService.deleteAllByUserId(id);
			accountService.deleteAllByUserId(id);
			userService.delete(user.getUserId());
			return new ResponseEntity<User>(HttpStatus.ACCEPTED);
		}
		
		
	}
	@GetMapping("/user")
	public ResponseEntity<User> getUser(HttpSession session)
	{
		User userData = null;
		// deletes User from database
		// TODO:: Security, maybe sessions or something to prevent people from editing everyones account
		if(session.getAttribute("email") != null)
		{
			userData = userService.findUserByEmail(session.getAttribute("email").toString());
			User userReturn = new User();
			userReturn.setEmail(userData.getEmail());
			userReturn.setFirstName(userData.getFirstName());
			userReturn.setLastName(userData.getLastName());
			userReturn.setCreatedAt(userData.getCreatedAt());
			userReturn.setUpdatedAt(userData.getUpdatedAt());
			userReturn.setUserId(userData.getUserId());
			userReturn.setPassword("");
			return new ResponseEntity<User>(userReturn,HttpStatus.FOUND);
		}
		else 
		{
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping("/logout")
	public ResponseEntity<User> logout(HttpSession session)
	{
		
		
		// TODO:: Security, maybe sessions or something to prevent people from editing everyones account
		if(session.getAttribute("email") != null)
		{
			session.invalidate();
			return new ResponseEntity<User>(HttpStatus.ACCEPTED);
		}
		else 
		{
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
	}
	
}
