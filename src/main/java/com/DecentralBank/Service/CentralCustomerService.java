package com.DecentralBank.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DecentralBank.Entity.BankCustomer;
import com.DecentralBank.Entity.CustomerDetails;
import com.DecentralBank.Entity.Customers;
import com.DecentralBank.Handler.Request;
import com.DecentralBank.Operations.CentralOperations;
import com.DecentralBank.Repository.BankCustomerRepository;
import com.DecentralBank.Repository.CustomerDetailsRepository;
import com.DecentralBank.Repository.CustomersRepository;

import jakarta.transaction.Transactional;

@Service
public class CentralCustomerService implements DecentralCustomerService {

	@Autowired
	BankCustomerRepository bankcustomerRepo;

	@Autowired
	CustomersRepository customersRepo;

	@Autowired
	CustomerDetailsRepository customerDetailRepo;

	public String addNewCustomer(BankCustomer bankCustomer) {

		String page = "";

		boolean mpinInput = CentralOperations.ValidateInputValue(bankCustomer.getMpin(), "\\d{4}");
		boolean mtpinInput = CentralOperations.ValidateInputValue(bankCustomer.getMtpin(), "\\d{4}");
		boolean phonenumberInput = CentralOperations.ValidateInputValue(bankCustomer.getPhoneNumber(), "\\d{10}");

		if (phonenumberInput == true) {
			if (mpinInput == true && mtpinInput == true) {
				// BankCustomer Entity Attributes and Values
				bankCustomer.setAccountNumber(bankCustomer.getAccountNumber());
				bankCustomer.setCustomerName(bankCustomer.getCustomerName());
				bankCustomer.setEmail(bankCustomer.getEmail());
				bankCustomer.setPassword(bankCustomer.getPassword());
				bankCustomer.setPhoneNumber(bankCustomer.getPhoneNumber());
				;
				bankCustomer.setMpin(bankCustomer.getMpin());
				bankCustomer.setMtpin(bankCustomer.getMtpin());
				bankCustomer.setAccountType(bankCustomer.getAccountType());
				bankCustomer.setCity(bankCustomer.getCity());
				bankCustomer.setState(bankCustomer.getState());
				bankCustomer.setBalance(bankCustomer.getBalance());
				bankCustomer.setLastUpdated(bankCustomer.getLastUpdated());
				bankCustomer.setCreated(bankCustomer.getCreated());
				// Customers Entity Attributes And Values
				Customers customers = new Customers();

				customers.setAccountNumber(bankCustomer.getAccountNumber());
				customers.setCustomerName(bankCustomer.getCustomerName());
				customers.setPassword(bankCustomer.getPassword());
				customers.setBankcustomer(bankCustomer);
				// Customer Details Entity Attributes and Values
				CustomerDetails customerDetails = new CustomerDetails();
				customerDetails.setAccountType(bankCustomer.getAccountType());
				customerDetails.setBalance(bankCustomer.getBalance());
				customerDetails.setCity(bankCustomer.getCity());
				customerDetails.setEmail(bankCustomer.getEmail());
				customerDetails.setPassword(bankCustomer.getPassword());
				customerDetails.setPhoneNumber(bankCustomer.getPhoneNumber());
				customerDetails.setState(bankCustomer.getState());
				customerDetails.setMpin(bankCustomer.getMpin());
				customerDetails.setMtpin(bankCustomer.getMtpin());
				// Setting up relationships
				bankCustomer.setCustomer(customers);
				bankCustomer.setCustomerDetails(customerDetails);
				customerDetails.setBankcustomer(bankCustomer);
				// Save data's
				bankcustomerRepo.save(bankCustomer);
				page = "redirect:/newcustomer?success";
			} else {
				page = "redirect:/newcustomer?invalidPin";
			}

		} else {
			page = "redirect:/newcustomer?invalidPN";
		}

		return page;

	}

	@Override
	public String CheckingAccountNumberAndPassword(String accountNumber, String password) {
		return customersRepo.getBankCustomerIDByAccountNumberAndPassword(accountNumber, password);
	}

	@Override
	public List<Customers> getCustomersData(int id) {
		return customersRepo.findById(id);
	}

	@Override
	public List<CustomerDetails> getCustomerDetailsData(int id) {
		return customerDetailRepo.findById(id);
	}

	@Override
	public String getBalanceById(int id) {
		return bankcustomerRepo.getBalanceByID(id);
	}

	@Override
	public void updateCustomerBalanceOnBankCustomer(int id, String balance) {
		bankcustomerRepo.updateCustomerBalanceByIdOnBankCustomer(id, balance);
	}

	@Override
	public String getBalanceId(int id) {
		return customerDetailRepo.getBalanceID(id);
	}

	@Override
	public void updateCustomerBalanceOnCustomerDetails(int id, String balance) {
		customerDetailRepo.updateCustomerBalanceByIdOnCustomerDetails(id, balance);
	}

	@Override
	public String getIdByAccountNumber(String AccountNumber) {
		return bankcustomerRepo.getIDByAccountNumber(AccountNumber);
	}

	@Override
	public String getMTPINById(int id) {
		return bankcustomerRepo.getMTPINById(id);
	}

	@Override
	public String getMPINById(int id) {
		return bankcustomerRepo.getMPINById(id);
	}

	@Override
	public String getPassword(String accountnumber, String password) {
		return customersRepo.getPassword(accountnumber, password);
	}

	@Override
	public String getAccountNumber(String accountnumber, String password) {
		return customersRepo.getaccountNumber(accountnumber, password);
	}

	@Override
	public void DeleteCustomer(int id) {
		bankcustomerRepo.deleteById(id);
	}

	
}
