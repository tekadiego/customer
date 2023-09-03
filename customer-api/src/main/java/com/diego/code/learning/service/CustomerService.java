package com.diego.code.learning.service;


import com.diego.code.learning.domain.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long id);
    Customer createCustomer(Customer customer);
    Customer update(Customer customer);
    void deleteCustomerById(Long id);
}
