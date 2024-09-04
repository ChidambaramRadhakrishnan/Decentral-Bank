package com.DecentralBank.Control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.DecentralBank.Entity.BankCustomer;
import com.DecentralBank.Entity.CustomerDetails;
import com.DecentralBank.Entity.Customers;
import com.DecentralBank.Operations.CentralOperations;
import com.DecentralBank.Service.CentralCustomerService;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.servlet.http.HttpSession;

@Controller
public class CustomerControl {

	@Autowired
	CentralCustomerService customerservice;

	@GetMapping("newcustomer")
	public String addCustomer(Model model) {
		model.addAttribute("users", new BankCustomer());
		return "NewCustomer";
	}

	@PostMapping("newcustomer/add")
	public String registerAdmin(@ModelAttribute BankCustomer bankcustomer) {
		String page = customerservice.addNewCustomer(bankcustomer);
		return page;
	}

	@GetMapping("/loginuser")
	public String cusomterLogin(Model model) {
		Customers customer = new Customers();
		model.addAttribute("customerLoginData", customer);
		return "CustomerLogin";
	}
	
	@RequestMapping("/deletecustomer/{id}")
	public String deleteCustomer(@PathVariable int id) {
		customerservice.DeleteCustomer(id);
		return "redirect:/adminIndex?customersuccess";
	}

	@GetMapping("/loginuser/login")
	public String customerIndex(HttpSession session, Model model, @RequestParam("accountNumber") String accountNumber,
			@RequestParam("password") String password) {
		String id = customerservice.CheckingAccountNumberAndPassword(accountNumber, password);
		String ac = customerservice.getAccountNumber(accountNumber, password);
		String pass = customerservice.getPassword(accountNumber, password);
		System.out.println(ac+" "+pass+" ------------------------------");
		String page ="";
		if(ac != null && pass!= null) {
			if (!id.equals(null)) {
				int bankcustomerid = Integer.parseInt(id);
				session.setAttribute("id", id);
				Optional<Customers> datas = customerservice.getCustomersData(bankcustomerid).stream()
						.filter(e -> e.getId() == bankcustomerid).findFirst();
				Optional<CustomerDetails> customerdatas = customerservice.getCustomerDetailsData(bankcustomerid).stream()
						.filter(e -> e.getId() == bankcustomerid).findFirst();
				//Model 
				model.addAttribute("allcustomerdetailsdata", customerdatas.get());
				//Session 
				session.setAttribute("customerName", datas.get().getCustomerName());
				session.setAttribute("accountNumber", datas.get().getAccountNumber());
				// Model
				model.addAttribute("customerName", (String) session.getAttribute("customerName"));
				model.addAttribute("allcustomerdata", datas.get());
				page = "CustomerIndex";
			}else {
				page = "redirect:/loginuser?notfound";
			}
			
		}else {
			page = "redirect:/loginuser?notfound";
		}
		
		return page;
		
	}

	@GetMapping("/customserindex")
	public String customerIndex(HttpSession session, Model model) {
		int bankcustomerid = Integer.parseInt((String) session.getAttribute("id"));
		// Using optional to retrieve data by id which we got 
		Optional<Customers> datas = customerservice.getCustomersData(bankcustomerid).stream()
				.filter(e -> e.getId() == bankcustomerid).findFirst();
		Optional<CustomerDetails> customerdatas = customerservice.getCustomerDetailsData(bankcustomerid).stream()
				.filter(e -> e.getId() == bankcustomerid).findFirst();
		// Model Attributes for further usage.
		model.addAttribute("allcustomerdetailsdata", customerdatas.get());
		session.setAttribute("customerName", datas.get().getCustomerName());
		session.setAttribute("accountNumber", datas.get().getAccountNumber());
		model.addAttribute("customerName", (String) session.getAttribute("customerName"));
		model.addAttribute("allcustomerdata", datas.get());
		return "CustomerIndex";
	}

	@GetMapping("/fundtransfer")
	public String fundTransaction(Model model, HttpSession session) {
		model.addAttribute("customerName", (String) session.getAttribute("customerName"));
		model.addAttribute("accountNumber", (String) session.getAttribute("accountNumber"));
		BankCustomer bankcustomer = new BankCustomer();
		model.addAttribute("customer", bankcustomer);
		int id = Integer.parseInt((String) session.getAttribute("id"));
		// Fetching balance for show up here
		String Available_Balance = customerservice.getBalanceById(id);
		// Here we do convertions to convert number to rupees.
		model.addAttribute("balance", CentralOperations.RupeeConvertion(Available_Balance));
		return "FundTransfer";
	}

	@GetMapping("/fundTransferred")
	public String fundTransferred(Model model, HttpSession session, @RequestParam("accountNumber") String accountNumber,
			@RequestParam("balance") String amount, @RequestParam("mtpin") String mtpin) {
		String page = "";
		// Getting id by current session data
		int id = Integer.parseInt((String) session.getAttribute("id"));
		
		boolean inputAccountNumber = CentralOperations.ValidateInputValue(accountNumber, "DB\\d{8}");
		
		if(inputAccountNumber == true) { // validating account number input
			
			boolean inputAmount = CentralOperations.ValidateInputValue(amount, "\\d+");
			
			if(inputAmount == true) { // validating amount input
				
				boolean inputmtpin = CentralOperations.ValidateInputValue(mtpin, "\\d{4}");
				
				if(inputmtpin == true) { // validaing mpin input
					
					String customer_MTPIN = customerservice.getMTPINById(id); // Getting MTPIN to verify.
					if (customer_MTPIN.equals(mtpin)) {
						// First get current customer balance and subtract by transfer amount then
						// stored as updated balance
						String balance = customerservice.getBalanceById(id);
						int current_Balance = Integer.parseInt(balance);
						int updated_Balance = current_Balance - Integer.parseInt(amount);
						if (updated_Balance >= 0) { // making condition to if updated balance greater than to make transfer
							// updated amount into current customer balance into customer database
							customerservice.updateCustomerBalanceOnBankCustomer(id, String.valueOf(updated_Balance));
							customerservice.updateCustomerBalanceOnCustomerDetails(id, String.valueOf(updated_Balance));
							// Fetching id by account number which we want to transfer
							String id_temp = customerservice.getIdByAccountNumber(accountNumber);
							int beneficiary_id = Integer.parseInt(id_temp); // This is what returned id
							// here we are gonna get balance by id which we got from beneficiary account number
							String beneficiary_Balance = customerservice.getBalanceById(beneficiary_id);
							// updating amount/balance from beneficiary balance with transfer balance
							int beneficiary_current_Balance = Integer.parseInt(beneficiary_Balance);
							int updated_Transfer_Balance = beneficiary_current_Balance + Integer.parseInt(amount);
							System.out.println(" -- updaed _" + updated_Transfer_Balance);
							// Finally, here we are updating balance into beneficiary database
							customerservice.updateCustomerBalanceOnBankCustomer(beneficiary_id,
									String.valueOf(updated_Transfer_Balance));
							customerservice.updateCustomerBalanceOnCustomerDetails(beneficiary_id,
									String.valueOf(updated_Transfer_Balance));
							page = "redirect:/fundtransfer?success";
						} else { // If updated balance lesser than 0 or negative value(-100) it'll throw warning
							page = "redirect:/fundtransfer?balanceError";
						}
					} else { // if incorrect MTPIN 
						page = "redirect:/fundtransfer?error";
					}
				}else { // // If MTPIN isn't correct one throw this warning
					page = "redirect:/fundtransfer?invalidmtpin";
				}
				
			}else { // if amount input value is not invalid 
				page = "redirect:/fundtransfer?invalidamount";
			}
		}else { // if account number input value is not invalid
			page = "redirect:/fundtransfer?invalidAC";
		}

		

		return page;
	}

	@GetMapping("/fundDeposit")
	public String fundDeposit(Model model, HttpSession session) {
		model.addAttribute("customerName", (String) session.getAttribute("customerName"));
		model.addAttribute("accountNumber", (String) session.getAttribute("accountNumber"));
		BankCustomer bankcustomer = new BankCustomer();
		model.addAttribute("customer", bankcustomer);
		int id = Integer.parseInt((String) session.getAttribute("id"));
		// Fetching balance for show up here
		String Available_Balance = customerservice.getBalanceById(id);
		// Here we do convertions to convert number to rupees.
		model.addAttribute("balance", CentralOperations.RupeeConvertion(Available_Balance));
		return "FundDeposit";
	}

	@GetMapping("/fundDepositted")
	public String fundDepositted(Model model, HttpSession session, @RequestParam("balance") String amount,
			@RequestParam("mpin") String mpin) {
		String page = "";

		boolean inputMpin = CentralOperations.ValidateInputValue(mpin, "\\d{4}");

		if (inputMpin == true) {
			boolean inputAmount = CentralOperations.ValidateInputValue(amount, "\\d+");
			if (inputAmount == true) {
				// Getting id by current session data
				int id = Integer.parseInt((String) session.getAttribute("id"));
				String customer_MPIN = customerservice.getMPINById(id); // Getting MTPIN to verify.
				if (customer_MPIN.equals(mpin)) {
					// First get current customer balance and subtract by transfer amount then
					// stored as updated balance
					String balance = customerservice.getBalanceById(id);
					int current_Balance = Integer.parseInt(balance);
					int updated_Balance = current_Balance + Integer.parseInt(amount);
					// updated amount into current customer balance into customer database
					customerservice.updateCustomerBalanceOnBankCustomer(id, String.valueOf(updated_Balance));
					customerservice.updateCustomerBalanceOnCustomerDetails(id, String.valueOf(updated_Balance));
					page = "redirect:/fundDeposit?success";
				} else { // if incorrect mpin
					page = "redirect:/fundDeposit?error";
				}
			}else { // // If amount isn't correct one throw this warning
				page = "redirect:/fundDeposit?invalidAmount";
			}

		}else { // if mpin input is not valid
			page = "redirect:/fundDeposit?invalidmpin";
		}

		return page;
	}

}
