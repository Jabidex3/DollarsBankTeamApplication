package com.teamthree.dollarsbank.dollarsbankteamapplication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teamthree.dollarsbank.dollarsbankteamapplication.model.Transaction;
import com.teamthree.dollarsbank.dollarsbankteamapplication.repository.TransactionRepo;

@Service
public class TransactionServiceImplementation implements TransactionService {
	
	@Autowired
	private TransactionRepo tranRepo;
	
	@Override
	public void addTransaction(Transaction t) {
		tranRepo.save(t);
	}

	@Override
	public List<Transaction> findAll() {
		return tranRepo.findAll();
	}

}
