package com.DecentralBank.Entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.DecentralBank.Handler.Request;
import com.DecentralBank.Operations.CentralOperations;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "BankCustomer")
public class BankCustomer {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private int id;
	private String accountNumber;
	private String customerName;
	private String phoneNumber;
	private String email;
	private String password;
	private String mpin;
	private String mtpin;
	private String accountType;
	private String city;
	private String state;
	private String balance;
	@CreationTimestamp
	private Date Created;
	@UpdateTimestamp
	private Date LastUpdated;
	
	
	@OneToOne(mappedBy = "bankcustomer", cascade = CascadeType.ALL, orphanRemoval = true)
	private Customers Customer;
	
	@OneToOne(mappedBy = "bankcustomer", cascade = CascadeType.ALL, orphanRemoval = true)
	private CustomerDetails customerDetails;
	
	@PrePersist
	public void GenerateAcconutNumber() {
		this.accountNumber = CentralOperations.CustomerAccountNumberGenerator();
		this.Customer.setAccountNumber(accountNumber);
	}


	public Customers getCustomer() {
		return Customer;
	}

	public void setCustomer(Customers customer) {
		Customer = customer;
	}
	
	public Date getCreated() {
		return Created;
	}


	public void setCreated(Date created) {
		Created = created;
	}


	public Date getLastUpdated() {
		return LastUpdated;
	}


	public void setLastUpdated(Date lastUpdated) {
		LastUpdated = lastUpdated;
	}

	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public BankCustomer(Request reqhandler) {
		this.customerName = reqhandler.getCustomerName();
	}
	
	public BankCustomer() {
		
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
	
	

	

}
