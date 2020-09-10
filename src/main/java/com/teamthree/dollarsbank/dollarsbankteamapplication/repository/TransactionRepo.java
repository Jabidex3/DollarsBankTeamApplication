package com.teamthree.dollarsbank.dollarsbankteamapplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamthree.dollarsbank.dollarsbankteamapplication.model.Transaction;


public interface TransactionRepo extends JpaRepository<Transaction,Integer>{
	public List<Transaction> findAllByUserId(int userId);
}
