package com.example.demo.customer;

// POJO class
public class Customer {
	private final Integer customerId;
	private final String customerName;
	
	// Constructor
	public Customer(Integer customerId, String customerName) {
		this.customerId = customerId;
		this.customerName = customerName;
	}

	// Getters
	public Integer getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}
	
	// toString method
	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + "]";
	}
	
}
