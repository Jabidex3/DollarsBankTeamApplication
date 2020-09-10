package com.teamthree.dollarsbank.dollarsbankteamapplication.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.teamthree.dollarsbank.dollarsbankteamapplication.model.Account;
import com.teamthree.dollarsbank.dollarsbankteamapplication.model.Transaction;
import com.teamthree.dollarsbank.dollarsbankteamapplication.service.AccountService;
import com.teamthree.dollarsbank.dollarsbankteamapplication.service.TransactionService;

@RestController
@RequestMapping("/")
public class TransactionController {
	
	private Statement st;
	private Connection conn;
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
	private AccountService accService;
	
	@Autowired
	private TransactionService tranService;
	
	@PostMapping("transaction")
	public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction t){
		if(t.getAction().toUpperCase().equals("WITHDRAW")) {
			t.setAction(t.getAction().toUpperCase());
			if(t.getAmount()<=0) {
				return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST);
			}
			
			ArrayList<Account>allAccounts = (ArrayList<Account>) accService.findAll();			
			for(int i=0;i<allAccounts.size();i++) {
				if(allAccounts.get(i).getAccountId()==t.getFromAccountId()) {
					if(allAccounts.get(i).getBalance()<t.getAmount()) {
						return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST);
					}
					else {
						tranService.addTransaction(t);
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
			return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST);
		}
		else if(t.getAction().toUpperCase().equals("MONEY TRANSFER")) {
			t.setAction(t.getAction().toUpperCase());
			if(t.getAmount()<=0 || t.getToAccountId()==0) {
				return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST);
			}
			ArrayList<Account>allAccounts = (ArrayList<Account>) accService.findAll();
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
				tranService.addTransaction(t);
//				if(user.getUserId()!=recipient.getUserId()) {
					Transaction r = new Transaction();
					r.setUserId(recipient.getUserId());
					r.setAction(t.getAction());
					r.setAmount(t.getAmount());
					r.setFromAccountId(t.getFromAccountId());
					r.setToAccountId(t.getToAccountId());
					tranService.addTransaction(r);
//				}
				
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
		else {//action value is not valid choice
			return new ResponseEntity<Transaction>(HttpStatus.BAD_REQUEST);
		}
	}
}
