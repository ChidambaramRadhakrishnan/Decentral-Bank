package com.DecentralBank.Service;

import java.util.List;

import com.DecentralBank.Entity.Admin;
import com.DecentralBank.Entity.BankCustomer;

public interface DecentralAdminService {
	
	public Admin AddAdmin(Admin admin);
	public List<Admin> ValidateAdminIdandPassword(String adminid, String adminpassword);
	public List<Admin> GetAllAdmin();
	public void DeleteAdmin(int userID);
	public List<BankCustomer> getAllCustomers();
	public String getAllDepositAmounts();

}
