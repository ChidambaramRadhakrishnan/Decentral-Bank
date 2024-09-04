package com.DecentralBank.Service;

import java.util.List;

import com.DecentralBank.Entity.BankCustomer;
import com.DecentralBank.Entity.CustomerDetails;
import com.DecentralBank.Entity.Customers;

public interface DecentralCustomerService {
	
	String addNewCustomer(BankCustomer bankCustomer);
	String CheckingAccountNumberAndPassword(String accountNumber, String password); 
	List<Customers> getCustomersData(int id);
	List<CustomerDetails> getCustomerDetailsData(int id);
	String getBalanceById(int id);
	void updateCustomerBalanceOnBankCustomer(int id,String balance);
	public String getBalanceId(int id);
	public void updateCustomerBalanceOnCustomerDetails(int id, String balance);
	public String getIdByAccountNumber(String AccountNumber);
	String getMTPINById(int id);
	String getMPINById(int id);
	String getPassword(String accountnumber,String password);
	String getAccountNumber(String accountnumber, String password);
	void DeleteCustomer(int id);
}
