package com.teamthree.dollarsbank.dollarsbankteamapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.teamthree.dollarsbank.dollarsbankteamapplication.model.Account;


public interface AccountRepository extends JpaRepository<Account, Integer> {

}