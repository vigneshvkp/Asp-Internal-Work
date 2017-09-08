package com.aspire.thi.repository;

import java.util.List;

import com.aspire.thi.domain.Customer;

public interface CustomerRepository {

    public List<Customer> getAllCustomers();

    public void saveCustomer(Customer customer);
    
    public void syncCustomer(Customer customer);

}