package com.aspire.thi.service;

import java.util.List;

import com.aspire.thi.domain.Customer;

public interface ICustomerManager {
	
	public void saveCustomer(Customer customer);

	public void saveAllCustomers(List<Customer> customers);
	
	public Customer getCustomer(int id);
	
	public List<Customer> getAllActiveCustomers();
	
	public List<Customer> getAllInactiveCustomers();
	
	public void syncCustomers(List<Customer> customers);
	
}
