package com.diego.code.learning.service;

import com.diego.code.learning.domain.Customer;
import com.diego.code.learning.exception.CustomerNotFoundException;
import com.diego.code.learning.exception.EmailAlreadyExistException;
import com.diego.code.learning.repository.CustomerRepository;
import static org.junit.Assert.assertThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomerServiceImplTest {

    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;
    private AutoCloseable closable;

    @BeforeEach
    void setUp() {
        closable = MockitoAnnotations.openMocks(this);
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        closable.close();
    }

    @Test
    void getAllCustomers() {
        //When
        customerService.getAllCustomers();
        //Then
        verify(customerRepository).findAll();
    }

    @Test
    void getCustomerById() {
        //given
        Long id= 1L;
        //When
        when(customerRepository.findById(id)).thenReturn(Optional.of(getCustomer()));
        customerService.getCustomerById(id);
        //Then
        verify(customerRepository).findById(id);
    }

    @Test
    void getCustomerByIdShouldThrowCustomerNotFoundException() {
        //given
        Long id= 1L;
        //When
        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class , () ->{
            customerService.getCustomerById(id);
        });
    }

    @Test
    void createCustomer() {
        //given
        Customer customer= getCustomer();
        //When
        customerService.createCustomer(customer);
        //Then
        verify(customerRepository).save(customer);
    }

    @Test
    void createCustomerShouldThrowEmailAlreadyExistException() {
        //given
        Customer customer= getCustomer();
        //When
        when(customerRepository.existsCustomerByEmail(customer.getEmail())).thenReturn(true);
        assertThrows(EmailAlreadyExistException.class , () ->{
            customerService.createCustomer(customer);
        });
    }

    private Customer getCustomer() {
        return Customer.builder()
                .name("diego")
                .age(25)
                .email("diego@gmail.com")
                .build();
    }

    @Test
    void update() {
        //given
        Customer customer= Customer.builder()
                .id(1L)
                .name("diego")
                .age(25)
                .email("diego@gmail.com")
                .build();
        //When
        when(customerRepository.existsById(customer.getId())).thenReturn(true);
        customerService.update(customer);

        //Then
        verify(customerRepository).save(customer);
    }

    @Test
    void updateShouldThrowCustomerNotFoundException() {
        //given
        Customer customer= Customer.builder()
                .id(1L)
                .name("diego")
                .age(25)
                .email("diego@gmail.com")
                .build();
        //When
        when(customerRepository.existsById(customer.getId())).thenReturn(false);
        assertThrows(CustomerNotFoundException.class , () ->{
            customerService.update(customer);
        });
    }

    @Test
    void deleteCustomerById() {
        //given
        Long id= 1L;
        //When
        when(customerRepository.existsById(id)).thenReturn(true);
        customerService.deleteCustomerById(id);
        //Then
        verify(customerRepository).deleteById(id);
    }

    @Test
    void deleteCustomerByIdShouldThrowCustomerNotFoundException() {
        //given
        Long id= 1L;
        //When
        when(customerRepository.existsById(id)).thenReturn(false);
        assertThrows(CustomerNotFoundException.class , () ->{
            customerService.deleteCustomerById(id);
        });
    }
}