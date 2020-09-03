package com.teamthree.dollarsbank.service;

import java.util.List;

import com.teamthree.dollarsbank.model.User;

public interface UserService
{
	public void findUserById(int id);
	void addUser(User user);
	boolean userExists(String name);
	public User findUserByName(String userName);
	public List<User> findAll();
}
