package com.diego.code.learning.service;

import com.diego.code.learning.domain.Customer;
import com.diego.code.learning.exception.CustomerNotFoundException;
import com.diego.code.learning.exception.EmailAlreadyExistException;
import com.diego.code.learning.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements  CustomerService{

    private  final CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer with id "+ id + " Not found"));
    }

    @Override
    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsCustomerByEmail(customer.getEmail())){
            throw new EmailAlreadyExistException("Email already taken");
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Customer customer) {
        isCustomerExist(customer.getId());
        return customerRepository.save(customer);
    }

    private void isCustomerExist(Long id) {
        if( !customerRepository.existsById(id) ){
            throw new CustomerNotFoundException("Customer you want to update Not found");
        }
    }

    @Override
    public void deleteCustomerById(Long id) {
        isCustomerExist(id);
        customerRepository.deleteById(id);
    }
}
