package com.DecentralBank.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DecentralBank.Entity.Admin;
import com.DecentralBank.Entity.BankCustomer;
import com.DecentralBank.Repository.AdminRepository;
import com.DecentralBank.Repository.BankCustomerRepository;

@Service
public class CentralAdminService implements DecentralAdminService{
	
	@Autowired
	AdminRepository adminRepository;
	
	@Autowired
	BankCustomerRepository bankCustomerRepository;

	@Override
	public Admin AddAdmin(Admin admin) {
		adminRepository.save(admin);
		return admin;
	}

	@Override
	public List<Admin> ValidateAdminIdandPassword(String adminid, String adminpassword) {
		return adminRepository.findByUserIDAndPassword(adminid, adminpassword);
	}

	@Override
	public List<Admin> GetAllAdmin() {
		return adminRepository.findAll();
	}

	@Override
	public void DeleteAdmin(int userID) {
		adminRepository.deleteById(userID);
	}

	@Override
	public List<BankCustomer> getAllCustomers() {
		return bankCustomerRepository.findAll();
	}

	@Override
	public String getAllDepositAmounts() {
		return adminRepository.getAllDposits();
	}

	
}
