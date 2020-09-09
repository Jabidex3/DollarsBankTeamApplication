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
	private int accountId;
	
	private int userId;
	private String accountType;
	private double balance;
	
	public Account() {
		super();
	}

	public Account(int userId, String accountType, double balance) {
		super();
		this.userId = userId;
		this.accountType = accountType;
		this.balance = balance;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account [accountId=" + accountId + ", userId=" + userId + ", accountType=" + accountType + ", balance="
				+ balance + "]";
	}

	
	
	
}
