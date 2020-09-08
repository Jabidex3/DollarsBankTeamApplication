package com.teamthree.dollarsbank.dollarsbankteamapplication.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="accounts")
public class Account {
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int account_id;
	
	private int user_id;
	private String account_type;
	private double balance;
	
	public Account() {
		super();
	}

	public Account(int user_id, String account_type, double balance) {
		super();
		this.user_id = user_id;
		this.account_type = account_type;
		this.balance = balance;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [account_id=" + account_id + ", user_id=" + user_id + ", account_type=" + account_type
				+ ", balance=" + balance + "]";
	}
	
}
