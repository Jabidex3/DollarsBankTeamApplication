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
		acc.setAccountId(accNum);
		if(acc.getAccountType()==null) {//default checking account if account_type field not provided
			acc.setAccountType("CHECKING");
		}
		else {
			acc.setAccountType(acc.getAccountType().toUpperCase());
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
			if(a.getAccountId()==x) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Account findById(int accountId)
	{
		// TODO Auto-generated method stub
		return accRepo.findByAccountId(accountId);
	}

	@Override
	public void save(Account account)
	{
		// TODO Auto-generated method stub
		accRepo.save(account);
	}

	@Override
	public void deleteAllByUserId(int userId)
	{
		// TODO Auto-generated method stub
		accRepo.deleteAll(accRepo.findAllByUserId(userId));
		
	}
	
	
}
