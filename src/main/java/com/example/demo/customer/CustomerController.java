package com.example.demo.customer;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController{
	
	// List of customers
	private static final List<Customer> CUSTOMERS = Arrays.asList(
			new Customer(1, "Edin"),
			new Customer(2, "Ivan"),
			new Customer(3, "Denzel")
			);
	
	// The customerId is the path variable in this case
	// This method returns a list of customers filtering the result by their ids
	@GetMapping(path = "{customerId}")
	public Customer getCustomer(@PathVariable("customerId") Integer customerId) {
		return CUSTOMERS.stream()
				.filter(customer -> customerId.equals(customer.getCustomerId()))
				.findFirst()
				.orElseThrow(() -> new IllegalStateException("Customer " + customerId + " does not exist."));
	}
}
