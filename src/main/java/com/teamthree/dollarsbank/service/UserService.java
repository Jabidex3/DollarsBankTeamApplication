package com.teamthree.dollarsbank.service;

import java.util.List;
import java.util.Optional;

import com.teamthree.dollarsbank.model.User;

public interface UserService
{
	public Optional<User> findUserById(int id);
	void addUser(User user);
	boolean userExists(String name);
	public User findUserByEmail(String email);
	public void delete(int id);
	public List<User> findAll();
}
