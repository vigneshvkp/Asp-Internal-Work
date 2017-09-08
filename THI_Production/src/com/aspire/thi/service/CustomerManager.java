package com.aspire.thi.service;

import java.util.List;

import com.aspire.thi.domain.Customer;
import com.aspire.thi.repository.CustomerRepository;

public class CustomerManager implements ICustomerManager {

	private CustomerRepository customerDao;
	
	public void setCustomerDao(CustomerRepository customerDao){
		this.customerDao = customerDao;
	}
	
	@Override
	public void saveCustomer(Customer customer) {
		customerDao.saveCustomer(customer);
	}

	@Override
	public void saveAllCustomers(List<Customer> customers) {
		for (Customer customer : customers) {
			saveCustomer(customer);
		}
	}

	@Override
	public Customer getCustomer(int id) { 
		return null;
	}

	@Override
	public List<Customer> getAllActiveCustomers() {
		return null;
	}

	@Override
	public List<Customer> getAllInactiveCustomers() {
		return null;
	}

	@Override
	public void syncCustomers(List<Customer> customers) {
		for (Customer customer : customers) {
			customerDao.syncCustomer(customer);
		}
	}
	
	

}
