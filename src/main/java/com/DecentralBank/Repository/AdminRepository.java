package com.DecentralBank.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.DecentralBank.Entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>{
	
	List<Admin> findByUserIDAndPassword(String userID, String password);
	
	@Query(value = "select sum(balance) from bank_customer;",nativeQuery = true)
	public String getAllDposits();
}
