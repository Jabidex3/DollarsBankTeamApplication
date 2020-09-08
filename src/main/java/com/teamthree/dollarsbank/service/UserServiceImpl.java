package com.teamthree.dollarsbank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.teamthree.dollarsbank.model.User;
import com.teamthree.dollarsbank.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService

{

		@Autowired
		private UserRepo userRepo;
		
		@Override
		public Optional<User> findUserById(int id)
		{
			
			return  userRepo.findById(id);
			
		}

		@Override
		public void addUser(User user)
		{
			userRepo.save(user);
			
		}

		@Override
		public boolean userExists(String email)
		{
			List<User> list = (List<User>)userRepo.findAll();
			for (User user : list)
			{
				if(user.getEmail().equals(email))
				{
					return true;
				}
			}
			return false;
		}

		@Override
		public User findUserByEmail(String email)
		{
		
			return userRepo.findByEmail(email);
		}

		@Override
		public List<User> findAll()
		{
			return (List<User>) userRepo.findAll();
		}

		@Override
		public void delete(int id)
		{
			userRepo.deleteById(id);
			
		}

	

}
