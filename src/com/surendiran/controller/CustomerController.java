package com.surendiran.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.surendiran.entity.Customer;
import com.surendiran.services.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	// need to inject the customer DAO
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/list")
	public String listCustomers(Model model) {
		
		// get the customers from DAO
		List<Customer> customers = customerService.getCustomers();
		
		// add the customers to the model
		model.addAttribute("customers", customers);
		
		return "list-customers";
	}
	
	@GetMapping("/showFormForAdd")
	public String createCustomerForm(Model myModel) {
		
		// create Model attribute to bind form data
		
		Customer theCustomer = new Customer();
		
		myModel.addAttribute("customer", theCustomer);
		return "create-customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer myCustomer) {
		
		System.out.println("Customer Id : " + myCustomer.getRowId());
		//save the customer using Service
		customerService.saveCustomer(myCustomer);

		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model myModel) {
		
		// get the customer from the database
		Customer customer = customerService.getCustomer(theId);
		
		// set customer as a model attribute to pre-populate the form
		myModel.addAttribute("customer", customer);
		
		// send over to our form
		return "create-customer-form";
	}
}