package com.teamthree.dollarsbank.service;

import java.util.List;

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
		public void findUserById(int id)
		{
			
			
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addUser(User user)
		{
			userRepo.save(user);
			// TODO Auto-generated method stub
			
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
			
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public User findUserByName(String email)
		{
		
			return userRepo.findByEmail(email);
		}

		@Override
		public List<User> findAll()
		{
			// TODO Auto-generated method stub
			return (List<User>) userRepo.findAll();
		}

	

}
