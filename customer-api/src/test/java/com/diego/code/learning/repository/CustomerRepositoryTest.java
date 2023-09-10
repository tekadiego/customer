package com.diego.code.learning.repository;

import com.diego.code.learning.AbstractTestContainer;
import com.diego.code.learning.domain.Customer;
import com.diego.code.learning.domain.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

//Connect to our Test container Db and load only necessary bean

@DataJpaTest // bring all that jpa need to run
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestContainer {

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.save(Customer.builder()
                .name("teka").age(25).email("tekadiego23@gmail.com").gender(Gender.MALE).build());
    }

    @Test
    void existsCustomerByEmail() {
        assertTrue(customerRepository.existsCustomerByEmail("tekadiego23@gmail.com"));
        assertFalse(customerRepository.existsCustomerByEmail("tekadiego26@gmail.com"));
    }
}