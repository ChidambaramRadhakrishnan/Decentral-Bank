package com.DecentralBank.Entity;

import com.DecentralBank.Handler.Request;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table( name = "CustomerDetails")
public class CustomerDetails {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private int id;
	
	private String password;
	
	private String accountType;
	private String balance;
	private String Email;
	private String City;
	private String State;
	private String PhoneNumber;
	private String mpin;
	private String mtpin;
	
	
	public String getMpin() {
		return mpin;
	}


	public void setMpin(String mpin) {
		this.mpin = mpin;
	}


	public String getMtpin() {
		return mtpin;
	}


	public void setMtpin(String mtpin) {
		this.mtpin = mtpin;
	}
	@OneToOne
    @JoinColumn(name = "bankCustomerID")
    private BankCustomer bankcustomer;
	

	

	public BankCustomer getBankcustomer() {
		return bankcustomer;
	}


	public void setBankcustomer(BankCustomer bankcustomer) {
		this.bankcustomer = bankcustomer;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getAccountType() {
		return accountType;
	}



	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


	public String getBalance() {
		return balance;
	}


	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	
	public CustomerDetails() {
		
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	
}
