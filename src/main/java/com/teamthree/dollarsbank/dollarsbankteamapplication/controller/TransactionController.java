package com.teamthree.dollarsbank.dollarsbankteamapplication.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamthree.dollarsbank.dollarsbankteamapplication.model.Account;
import com.teamthree.dollarsbank.dollarsbankteamapplication.model.Transaction;
import com.teamthree.dollarsbank.dollarsbankteamapplication.model.User;
import com.teamthree.dollarsbank.dollarsbankteamapplication.service.AccountService;
import com.teamthree.dollarsbank.dollarsbankteamapplication.service.TransactionService;
@RestController
@RequestMapping("/")
public class TransactionController {
	@Autowired
	AccountService accountService;
	@Autowired
	TransactionService transService; 
	
	@PostMapping("transaction")
	public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody Transaction t){
		Account account = accountService.findById(t.getFromAccountId());
		if(t.getAction().toUpperCase().equals("WITHDRAW")) {
			return new ResponseEntity<Transaction>(HttpStatus.ACCEPTED);
        }
        else if(t.getAction().toUpperCase().equals("DEPOSIT")) {
        	
        	if(t.getAmount() < 0)
        	{
        		 return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST);
        	}
        	else
        	{
        		account.setBalance(account.getBalance()+t.getAmount());
        		accountService.save(account);
        		transService.addTransaction(t);
        		return new ResponseEntity<Transaction>(HttpStatus.ACCEPTED);
        	}
        	
        }
        else if(t.getAction().toUpperCase().equals("MONEY TRANSFER")) {
        	return new ResponseEntity<Transaction>(HttpStatus.ACCEPTED);
        }
        else {
            return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST);
        }
	}
	
	
	
}
