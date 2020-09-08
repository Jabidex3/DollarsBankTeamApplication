package com.teamthree.dollarsbank.dollarsbankteamapplication.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.teamthree.dollarsbank.dollarsbankteamapplication.model.Account;
import com.teamthree.dollarsbank.dollarsbankteamapplication.repository.AccountRepository;

@Service
public class AccountServiceImplementation implements AccountService{
	@Autowired
	private AccountRepository accRepo;

	@Override
	public void addAccount(Account acc) {
		int minNum = 100000000;//min acc id num
		int maxNum = 999999999;//max acc id num
		int accNum =0;
		while(true) {
			accNum = (int)(Math.random()*(maxNum-minNum))+minNum;
			if(accIdExists(accNum)) {
				//if acc id num is already taken, look for another num 
			}
			else {
				break;
			}
		}
		acc.setAccount_id(accNum);
		if(acc.getAccount_type()==null) {//default checking account if account_type field not provided
			acc.setAccount_type("CHECKING");
		}
		else {
			acc.setAccount_type(acc.getAccount_type().toUpperCase());
		}
		
		
		accRepo.save(acc);
	}

	@Override
	public List<Account> findAll() {
		return accRepo.findAll();
	}

	@Override
	public boolean accIdExists(int x) {
		List<Account> accs = accRepo.findAll();
		for(Account a : accs) {
			if(a.getAccount_id()==x) {
				return true;
			}
		}
		return false;
	}
	
	
}
