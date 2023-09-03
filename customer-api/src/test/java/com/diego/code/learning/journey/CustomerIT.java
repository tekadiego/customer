package com.diego.code.learning.journey;

import com.diego.code.learning.AbstractTestContainer;
import com.diego.code.learning.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerIT extends AbstractTestContainer {

    public static final String CUSTOMER_URI = "/api/v1/customers";
    @Autowired
    private WebTestClient webTestClient;

    @Test
    void canRegisterACustomer() {
        //generate registration request

        //get all customers
        //make sure customer is present
        //get customer by id
        //DeleteCustomer

        //create Customer
        Customer customer = Customer.builder()
                .name("diego").email("tekadiego23@gmail.com").age(25).build();
         webTestClient.post().uri(CUSTOMER_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(customer), Customer.class)
                .exchange()
                .expectStatus().isOk();

        //get all customers
       List<Customer> customers =  webTestClient.get().uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                }).returnResult()
                .getResponseBody();
       assertThat(customers).hasSize(1);

        //get customer by id
        Customer savedCustomer =  webTestClient.get().uri(CUSTOMER_URI + "/{id}",customers.get(0).getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Customer>() {
                }).returnResult()
                .getResponseBody();
        assertThat( savedCustomer ).isNotNull();
        assertThat(savedCustomer.getEmail()).isEqualTo("tekadiego23@gmail.com");

        //update customer
        savedCustomer.setName("frank");
        webTestClient.put().uri(CUSTOMER_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(savedCustomer), Customer.class)
                .exchange()
                .expectStatus().isOk();

        //get customer by id
        Customer updatedSavedCustomer =  webTestClient.get().uri(CUSTOMER_URI + "/{id}",savedCustomer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Customer>() {
                }).returnResult()
                .getResponseBody();
        assertThat(updatedSavedCustomer.getName()).isEqualTo("frank");

        //deleteUser
        webTestClient.delete().uri(CUSTOMER_URI + "/{id}",savedCustomer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk();

        //get all customers should return Empty
        List<Customer> customersAfterDeletion =  webTestClient.get().uri(CUSTOMER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<Customer>() {
                }).returnResult()
                .getResponseBody();
        assertThat(customersAfterDeletion).isEmpty();
    }
}
