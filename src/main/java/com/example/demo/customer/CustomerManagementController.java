package com.example.demo.customer;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("management/api/v1/customers")
public class CustomerManagementController {
	
	// List of customers to be stored into the in-memory database
	private static final List<Customer> CUSTOMERS = Arrays.asList(
			new Customer(1, "Nicolo"),
			new Customer(2, "Marcelo"),
			new Customer(3, "Matteo")
			);
	
	//HTTP Methods
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
	public List<Customer> getAllCustomers(){
		return CUSTOMERS;
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('customer:write')")
	public void registerNewCustomer(Customer customer) {
		System.out.println(customer);
	}

	@DeleteMapping(path = "{customerId}")
	@PreAuthorize("hasAuthority('customer:write')")
	public void deleteCustomer(@PathVariable("customerId") Integer customerId) {
		System.out.println(customerId);
	}
	
	@PutMapping(path = "{customerId}")
	@PreAuthorize("hasAuthority('customer:write')")
	public void updateCustomer(@PathVariable("customerId") Integer customerId, @RequestBody Customer customer) {
		System.out.println(String.format("%s %s", customerId, customer));
	}
}