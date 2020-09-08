package com.teamthree.dollarsbank.dollarsbankteamapplication.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.beans.factory.annotation.Autowired;
// Declares the class as an JPA Entity
@Entity
// Names the table saved to the database as "users"
@Table(name = "users")
public class User
{
// Denotes the following variable is the Primary Id(key) and Is auto generated 
@Id
@GeneratedValue
private int userId;
private String firstName;
private String lastName;
@Column(unique = true)
private String email;
private String password;
// auto wired variables in beans allowing us to inject beans with values
@Autowired
public User()
{
	super();
	// TODO Auto-generated constructor stub
}
@Autowired
public User(String firstName, String lastName, String email, String password)
{
	super();
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	this.password = password;
}
public int getUserId()
{
	return userId;
}
public void setUserId(int userId)
{
	this.userId = userId;
}

public String getFirstName()
{
	return firstName;
}
public void setFirstName(String firstName)
{
	this.firstName = firstName;
}
public String getLastName()
{
	return lastName;
}
public void setLastName(String lastName)
{
	this.lastName = lastName;
}
public String getEmail()
{
	return email;
}
public void setEmail(String email)
{
	this.email = email;
}
public String getPassword()
{
	return password;
}
public void setPassword(String password)
{
	this.password = password;
}

}
