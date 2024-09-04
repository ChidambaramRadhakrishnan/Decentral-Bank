package com.DecentralBank.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.DecentralBank.Entity.BankCustomer;

import jakarta.transaction.Transactional;

public interface BankCustomerRepository extends JpaRepository<BankCustomer, Integer>{
	
	@Query(value = "select balance from bank_customer where id=:id;",nativeQuery = true)
	String getBalanceByID(int id);
		
	@Modifying
	@Transactional
	@Query(value = "update bank_customer set balance=:balance where id=:id;",nativeQuery = true)
	void updateCustomerBalanceByIdOnBankCustomer(int id,String balance);
	
	@Query(value = "select id from bank_customer where account_number = :accountNumber;",nativeQuery = true)
	String getIDByAccountNumber(String accountNumber);
	
	@Query(value = "select MTPIN from bank_customer where id = :id;", nativeQuery = true)
	String getMTPINById(int id);

	@Query(value ="select MPIN from bank_customer where id= :id;",nativeQuery = true)
	String getMPINById(int id);
}
