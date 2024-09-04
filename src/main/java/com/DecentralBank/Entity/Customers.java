package com.DecentralBank.Entity;

import com.DecentralBank.Handler.Request;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table( name = "Customers")
public class Customers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String accountNumber;
	private String customerName;
	private String password;
	
	
	@OneToOne
	@JoinColumn(name = "bankCustomerID")
	private BankCustomer bankcustomer;
	

	public BankCustomer getBankcustomer() {
		return bankcustomer;
	}

	public void setBankcustomer(BankCustomer bankcustomer) {
		this.bankcustomer = bankcustomer;
	}

	public Customers(Request reqhandler) {
		this.customerName = reqhandler.getCustomerName();
	}
	
	public Customers() {
		
	}

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

}
