package com.teamthree.dollarsbank.dollarsbankteamapplication.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamthree.dollarsbank.dollarsbankteamapplication.model.Account;
import com.teamthree.dollarsbank.dollarsbankteamapplication.model.Transaction;
import com.teamthree.dollarsbank.dollarsbankteamapplication.model.User;
import com.teamthree.dollarsbank.dollarsbankteamapplication.service.AccountService;
import com.teamthree.dollarsbank.dollarsbankteamapplication.service.TransactionService;
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
public class TransactionController {
	private Statement st;
	private Connection conn;
	private LocalDateTime ldt;
	public TransactionController() {
		String a ="jdbc:mysql://localhost:3306/teambankdatabase";
		String b ="root";
		String c="root";
		try {
			conn= DriverManager.getConnection(a,b,c);
			st =conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@Autowired
	AccountService accountService;
	@Autowired
	TransactionService transService; 
	
	@GetMapping("transaction/{accountId}")
	public ResponseEntity<List<Transaction>> getRecentTransactions(@Valid @PathVariable int accountId){
		//System.out.println(accountId);
		Account account = accountService.findById(accountId);
		System.out.println(account);
		int accNum = accountId;
		List<Transaction> allTransactions = transService.findAll();
		List<Transaction> specificTransactions = new ArrayList<Transaction>();
		for(int i=0;i<allTransactions.size();i++) {
			if(allTransactions.get(i).getFromAccountId()==accountId) {
				if(allTransactions.get(i).getAction().toUpperCase().equals("WITHDRAW")||allTransactions.get(i).getAction().toUpperCase().equals("DEPOSIT")) {
					specificTransactions.add(allTransactions.get(i));
				}
				else if(allTransactions.get(i).getAction().toUpperCase().equals("MONEY TRANSFER")) {
					if(allTransactions.get(i).getUserId()==account.getUserId()) {
						specificTransactions.add(allTransactions.get(i));
					}
				}
				
			}
			
			if(allTransactions.get(i).getToAccountId()==accountId) {
				if(allTransactions.get(i).getUserId()==account.getUserId()) {
					specificTransactions.add(allTransactions.get(i));
				}
			}
			
		}
		
		return new ResponseEntity<List<Transaction>>(specificTransactions,HttpStatus.ACCEPTED);
	}
	
	@PostMapping("transaction")
	public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody Transaction t){
		Account account = accountService.findById(t.getFromAccountId());
		account.setLastUpdated(ldt.now());
		if(t.getAction().toUpperCase().equals("WITHDRAW")) {
			t.setAction(t.getAction().toUpperCase());
			if(t.getAmount()<=0) {
				return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST);
			}
			
			ArrayList<Account>allAccounts = (ArrayList<Account>) accountService.findAll();			
			for(int i=0;i<allAccounts.size();i++) {
				if(allAccounts.get(i).getAccountId()==t.getFromAccountId()) {
					if(allAccounts.get(i).getBalance()<t.getAmount()) {
						return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST);
					}
					else {
						transService.addTransaction(t);
						double newBal = allAccounts.get(i).getBalance()-t.getAmount();
						//allAccounts.get(i).setBalance(newBal);
						
						PreparedStatement ps = null;
						String query = "update accounts set balance="+newBal+" where accountId="+allAccounts.get(i).getAccountId()+";";
						try {
							ps=conn.prepareStatement(query);
							ps.executeUpdate();
							return new ResponseEntity<Transaction>(HttpStatus.ACCEPTED);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						return new ResponseEntity<Transaction>(HttpStatus.ACCEPTED);
					}
				}
			}
			return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST); //not valid acc number
        }
        else if(t.getAction().toUpperCase().equals("DEPOSIT")) {
        	
        	if(t.getAmount() < 0)
        	{
        		 return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST);
        	}
        	else
        	{
        		account.setBalance(account.getBalance()+t.getAmount());
        		account.setLastUpdated(ldt.now());
        		accountService.save(account);
        		transService.addTransaction(t);
        		return new ResponseEntity<Transaction>(HttpStatus.ACCEPTED);
        	}
        	
        }
        else if(t.getAction().toUpperCase().equals("MONEY TRANSFER")) {
			t.setAction(t.getAction().toUpperCase());
			if(t.getAmount()<=0 || t.getToAccountId()==0) {
				return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST);
			}
			ArrayList<Account>allAccounts = (ArrayList<Account>) accountService.findAll();
			Account user = null;
			Account recipient = null;
			for(int i=0;i<allAccounts.size();i++) {
				if(allAccounts.get(i).getAccountId()==t.getFromAccountId()) {
					user = allAccounts.get(i);
				}
				else if(allAccounts.get(i).getAccountId()==t.getToAccountId()) {
					recipient = allAccounts.get(i);
				}
			}
			
			if(user==null || recipient==null) {//atleast one of the acc num's provided are invalid
				return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST);
			}
			
			if(user.getBalance()<t.getAmount()) {//not enough money to transfer
				return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST);
			}
			else {
				user.setLastUpdated(ldt.now());
				transService.addTransaction(t);
				if(user.getUserId()!=recipient.getUserId()) {
					Transaction r = new Transaction();
					r.setUserId(recipient.getUserId());
					r.setAction(t.getAction());
					r.setAmount(t.getAmount());
					r.setFromAccountId(t.getFromAccountId());
					r.setToAccountId(t.getToAccountId());
					transService.addTransaction(r);
				}
				
				double finalSenderBalance = user.getBalance()-t.getAmount();
				double finalRecipientBalance = recipient.getBalance()+t.getAmount();
				PreparedStatement ps = null;
				PreparedStatement ps2 = null;
				String query = "update accounts set balance="+finalSenderBalance+" where accountId="+user.getAccountId()+";";
				String query2 = "update accounts set balance="+finalRecipientBalance+" where accountId="+recipient.getAccountId()+";";
				try {
					ps=conn.prepareStatement(query);
					ps.executeUpdate();
					ps2=conn.prepareStatement(query2);
					ps2.executeUpdate();
					return new ResponseEntity<Transaction>(HttpStatus.ACCEPTED);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return new ResponseEntity<Transaction>(HttpStatus.ACCEPTED);
			}
			
			
        }
        else {
            return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST);
        }
	}
	
	
	
}
