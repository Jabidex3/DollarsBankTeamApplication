package com.teamthree.dollarsbank.dollarsbankteamapplication.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="transactions")
public class Transaction {
	@Id
	@GeneratedValue
	private int transactionId;
	
	private int accountId;
	private int toAccountId;
	private int userId;
	private String action;
	private double amount;
	private LocalDateTime createdAt = LocalDateTime.now();
	
	public Transaction() {
		super();
	}

	public Transaction(int accountId, int toAccountId, int userId, String action, double amount) {
		super();
		this.accountId = accountId;
		this.toAccountId = toAccountId;
		this.userId = userId;
		this.action = action;
		this.amount = amount;
	}

	
	
	public LocalDateTime getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt)
	{
		this.createdAt = createdAt;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(int toAccountId) {
		this.toAccountId = toAccountId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", accountId=" + accountId + ", toAccountId="
				+ toAccountId + ", userId=" + userId + ", action=" + action + ", amount=" + amount + ", createdAt="
				+ createdAt + "]";
	}

	
	
	
	

}
