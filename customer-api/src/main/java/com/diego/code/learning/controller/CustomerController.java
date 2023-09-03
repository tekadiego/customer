package com.diego.code.learning.controller;

import com.diego.code.learning.domain.Customer;
import com.diego.code.learning.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomer(){
        return   ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id){
        return   ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer){
        return   ResponseEntity.ok(customerService.createCustomer(customer));
    }

    @PutMapping
    public ResponseEntity<Customer> update(@RequestBody Customer customer){
        return   ResponseEntity.ok(customerService.update(customer));
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable("id") Long id){
           customerService.deleteCustomerById(id);
    }

}
