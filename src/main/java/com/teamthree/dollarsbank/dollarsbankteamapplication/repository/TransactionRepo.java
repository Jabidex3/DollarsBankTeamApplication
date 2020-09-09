package com.teamthree.dollarsbank.dollarsbankteamapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamthree.dollarsbank.dollarsbankteamapplication.model.Transaction;


public interface TransactionRepo extends JpaRepository<Transaction,Integer>{

}
