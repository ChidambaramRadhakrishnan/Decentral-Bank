package com.DecentralBank.Entity;

import com.DecentralBank.Operations.CentralOperations;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;


@Entity
@Table( name = "Admins")
public class Admin {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private int ID;	
	
	private String userID;
	private String username;
	private String password;
	

	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@PrePersist
	public void generateAdmin() {
		this.userID = CentralOperations.EmployeeIdGenerator();
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	
}
