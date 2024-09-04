package com.DecentralBank.Control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.DecentralBank.Entity.Admin;
import com.DecentralBank.Operations.CentralOperations;
import com.DecentralBank.Repository.AdminRepository;
import com.DecentralBank.Service.CentralAdminService;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminControl {

	@Autowired
	CentralAdminService service;

	@GetMapping("/")
	public String index() {
		return "Index";
	}

	@GetMapping("/addAdmin")
	public String addAdmin(Model model) {
		Admin admins = new Admin();
		model.addAttribute("AdminInput", admins);
		return "addAdmin";
	}

	@PostMapping("addadmin/save")
	public String saveAdmin(Admin admin) {
		service.AddAdmin(admin);
		ModelAndView view = new ModelAndView();
		view.addObject("AllAdmins", admin);
		return "redirect:/addAdmin?success";
	}

	@GetMapping("/adminLogin")
	public String loginAdmin(Model model) {
		Admin admin = new Admin();
		model.addAttribute("adminLoginData", admin);
		return "adminLogin";
	}

	@GetMapping("/adminpage")
	public String adminPage(@RequestParam("userID") String userid, @RequestParam("password") String password,
			Model model, HttpSession session) {
		// Validate whether user exist or not
		Optional<Admin> optional = service.ValidateAdminIdandPassword(userid, password).stream()
				.filter(l1 -> l1.getUserID().equals(userid) && l1.getPassword().equals(password)).findFirst();
		String page = "";
		if (optional.isPresent()) {
			// Model and view
			model.addAttribute("data", service.GetAllAdmin());
			model.addAttribute("allBankCustomerData", service.getAllCustomers());
			String depositAmount = service.getAllDepositAmounts();
			//
			model.addAttribute("alldepositamount", CentralOperations.RupeeConvertion(String.valueOf((Math.round(Double.parseDouble(depositAmount))))));
			// Set Attribute
			session.setAttribute("userid", userid);
			session.setAttribute("password", password);
			//
			session.setAttribute("username",optional.get().getUsername());
			model.addAttribute("username",(String) session.getAttribute("username"));
			page = "adminpage";
		} else { // If admin not found pop up a warning
			page = "redirect:/adminLogin?notfound";
		}
		return page;
	}

	@GetMapping("/adminIndex")
	public String adminIndex(HttpSession session, Model model) {
		model.addAttribute("data", service.GetAllAdmin());
		model.addAttribute("allBankCustomerData", service.getAllCustomers());
		String depositAmount = service.getAllDepositAmounts();
		//Retrieving total deposit amount and converted as rupees
		model.addAttribute("alldepositamount", CentralOperations.RupeeConvertion(String.valueOf((Math.round(Double.parseDouble(depositAmount))))));
		// Session Datas
		String userid = (String) session.getAttribute("userid");
		String password = (String) session.getAttribute("password");
		String username = (String) session.getAttribute("username");
		//Model Attributes
		model.addAttribute("username", username);
		
		Optional<Admin> optional = service.ValidateAdminIdandPassword(userid, password).stream()
				.filter(l1 -> l1.getUserID().equals(userid) && l1.getPassword().equals(password)).findFirst();
		return "adminpage";
	}

	@RequestMapping("/delete/{id}")
	public String DeleteAdmin(@PathVariable int id) {
		service.DeleteAdmin(id);
		return "redirect:/adminIndex?adminsuccess";
	}
	
	@GetMapping("/logout")
	public String Logout(HttpSession session) {
		session.invalidate();
		return "Index";
	}

}
