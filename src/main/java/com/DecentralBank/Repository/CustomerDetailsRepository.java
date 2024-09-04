package com.DecentralBank.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.DecentralBank.Entity.CustomerDetails;
import com.DecentralBank.Entity.Customers;

import jakarta.transaction.Transactional;

public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, Integer>{

	List<CustomerDetails> findById(int id);
	
	@Query(value = "select balance from customer_details where id=:id;",nativeQuery = true)
	String getBalanceID(int id);
	
	@Modifying
	@Transactional
	@Query(value = "update customer_details set balance=:balance where id=:id;",nativeQuery = true)
	void updateCustomerBalanceByIdOnCustomerDetails(int id,String balance);
}
