package com.teamthree.dollarsbank.dollarsbankteamapplication.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.teamthree.dollarsbank.dollarsbankteamapplication.model.Account;

@Service
public interface AccountService {
	public void addAccount(Account acc);
	public List<Account> findAll();
	public boolean accIdExists(int x);
	public Account findById(int accountId);
	public void save(Account account);
}
