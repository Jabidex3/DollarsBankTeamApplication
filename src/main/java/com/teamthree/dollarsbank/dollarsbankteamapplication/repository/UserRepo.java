package com.teamthree.dollarsbank.dollarsbankteamapplication.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.teamthree.dollarsbank.dollarsbankteamapplication.model.User;

// declares this interface as a repository
// CrudRepository automatically connects to our MySQL database and contains save, findAll, delete and many more methods 
@Repository("UserRepo")
public interface UserRepo extends CrudRepository<User, Integer>
{
	public User findByEmail(String email);
  
}
