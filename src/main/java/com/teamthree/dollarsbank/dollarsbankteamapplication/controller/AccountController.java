package com.teamthree.dollarsbank.dollarsbankteamapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamthree.dollarsbank.dollarsbankteamapplication.model.Account;
import com.teamthree.dollarsbank.dollarsbankteamapplication.repository.AccountRepository;
import com.teamthree.dollarsbank.dollarsbankteamapplication.service.AccountService;

@RestController
@RequestMapping("/")
public class AccountController {

	@Autowired
	private AccountService accService;
	
	@GetMapping("accounts")
	public List<Account> getAccounts(){
		return this.accService.findAll();
	}
	
	@PostMapping("createAccount")
	public ResponseEntity<Account> createAcc(@RequestBody Account acc) {
		if(acc.getUser_id()==0) {//no value provided from front end
			return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
		}
		else {
			accService.addAccount(acc);
			return new ResponseEntity<Account>(HttpStatus.CREATED);
		}
		
	}
}
