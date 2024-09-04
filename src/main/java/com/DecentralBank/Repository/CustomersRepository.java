package com.DecentralBank.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.DecentralBank.Entity.Customers;

public interface CustomersRepository extends JpaRepository<Customers, Integer>{

	@Query(value = "select bank_customerid from customers where account_number = :accountNumber and password= :password;",nativeQuery = true)
	String getBankCustomerIDByAccountNumberAndPassword(String accountNumber,String password);
	
	@Query(value = "select account_number from customers where account_number=:accountNumber and password=:password;",nativeQuery = true)
	String getaccountNumber(String accountNumber, String password);
	
	@Query(value = "select password from customers where account_number=:accountNumber and password=:password;",nativeQuery = true)
	String getPassword(String accountNumber, String password);
	
	List<Customers> findById(int id);
	
}
