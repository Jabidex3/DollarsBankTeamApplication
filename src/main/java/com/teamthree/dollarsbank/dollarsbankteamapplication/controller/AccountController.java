package com.teamthree.dollarsbank.dollarsbankteamapplication.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.teamthree.dollarsbank.dollarsbankteamapplication.model.Account;
import com.teamthree.dollarsbank.dollarsbankteamapplication.repository.AccountRepository;
import com.teamthree.dollarsbank.dollarsbankteamapplication.service.AccountService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
public class AccountController {
	private Statement st;
	private Connection conn;
	public AccountController() {
		try {

				String a ="jdbc:mysql://localhost:3306/teambankdatabase";
				String b ="root";
				String c="root";
				conn= DriverManager.getConnection(a,b,c);
				st =conn.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	
	@Autowired
	private AccountService accService;
	
//	@GetMapping("accountAll")//lists all user accounts
//	public List<Account> getAccounts(){
//		return this.accService.findAll();
//	}
	
	@GetMapping("account/{userId}")
	public ResponseEntity<List<Account>> getAccountsofSpecificUser(@Valid @PathVariable int userId){
		ArrayList<Account>allAccounts = (ArrayList<Account>) accService.findAll();
		ArrayList<Account>specificUserAccounts = new ArrayList<Account>();
		boolean userHasAcc = false;
		for(int i=0;i<allAccounts.size();i++) {
			if(allAccounts.get(i).getUserId()==userId) {
				userHasAcc=true;
				specificUserAccounts.add(allAccounts.get(i));
			}
		}
//		for(int j=0;j<specificUserAccounts.size();j++) {
//			System.out.println(specificUserAccounts.get(j));
//		}
		if(userHasAcc) {
			return new ResponseEntity<List<Account>>(specificUserAccounts,HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<List<Account>>(HttpStatus.NOT_FOUND);
		}
	//	return specificUserAccounts;
		
	}
	
	@GetMapping("account/home/{accId}")
	public ResponseEntity<Account> getAccByAccId(@Valid @PathVariable int accId) {
		Account account = accService.findById(accId);
		if(account ==null) {
			return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<Account>(account, HttpStatus.ACCEPTED);
		}
		
	}
	
	@PostMapping("account")
	public ResponseEntity<Account> createAcc(@RequestBody Account acc) {
		if(acc.getUserId()==0) {//no value provided from front end
			return new ResponseEntity<Account>(HttpStatus.BAD_REQUEST);
		}
		else {
			accService.addAccount(acc);
			return new ResponseEntity<Account>(HttpStatus.CREATED);
		}
		
	}
	
	//deletes all accounts associated with a user id, or specific account associated with an acc id
	@DeleteMapping("account/{user_id_or_acc_id}")
	public ResponseEntity<Account> deleteAcc(@PathVariable int user_id_or_acc_id) { 
		System.out.println(user_id_or_acc_id);
		ArrayList<Account>allAccounts = (ArrayList<Account>) accService.findAll();
		boolean validId=false;
		for(int i=0;i<allAccounts.size();i++) {
			if(allAccounts.get(i).getAccountId()==user_id_or_acc_id) {
				validId = true;
			}
			else if(allAccounts.get(i).getUserId()==user_id_or_acc_id) {
				validId = true;
			}
		}
		
		if(validId==false) {
			return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
		}
		PreparedStatement ps;
		try {
			if(user_id_or_acc_id>=100000000) {//delete specific account
				ps = conn.prepareStatement("delete from accounts where accountId="+user_id_or_acc_id);
				ps.executeUpdate();
				return new ResponseEntity<Account>(HttpStatus.ACCEPTED);
			}
			else {//delete all account records of matching user_id
				ps = conn.prepareStatement("delete from accounts where userId="+user_id_or_acc_id);
				ps.executeUpdate();
				return new ResponseEntity<Account>(HttpStatus.ACCEPTED);
			}
			
		} catch (SQLException e) {
			
		}
		return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
		
	}
}
