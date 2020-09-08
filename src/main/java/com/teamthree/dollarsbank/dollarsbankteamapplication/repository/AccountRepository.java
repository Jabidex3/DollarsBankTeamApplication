package com.teamthree.dollarsbank.dollarsbankteamapplication.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamthree.dollarsbank.dollarsbankteamapplication.model.Account;


public interface AccountRepository extends JpaRepository<Account, Integer> {
	//public List<Account> accByUserId(int user_id);
}
