package com.teamthree.dollarsbank.dollarsbankteamapplication.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.teamthree.dollarsbank.dollarsbankteamapplication.model.Transaction;

@Service
public interface TransactionService {
	public void addTransaction(Transaction t);
	public List<Transaction> findAll();
	public void deleteAllByUserId(int userId);
	
}
