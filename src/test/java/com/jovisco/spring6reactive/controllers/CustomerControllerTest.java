package com.jovisco.spring6reactive.controllers;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.jovisco.spring6reactive.model.CustomerDTO;

import reactor.core.publisher.Mono;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(100)
    void testGetAll() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .get().uri(CustomerController.CUSTOMER_PATH)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody().jsonPath("$.size()").isEqualTo(4);
    }

    @Test
    @Order(100)
    void testGetById() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .get().uri(CustomerController.CUSTOMER_ID_PATH, 1)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(CustomerDTO.class);
    }

    @Test
    @Order(100)
    void testGetByIdNotFound() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .get().uri(CustomerController.CUSTOMER_ID_PATH, 4711)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(200)
    void testCreateCustomer() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .post().uri(CustomerController.CUSTOMER_PATH)
            .body(Mono.just(CustomerDTO.builder().name("Spinner KG").build()), CustomerDTO.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().exists("Location");
    }

    @Test
    @Order(200)
    void testCreateCustomerWithValidationError() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .post().uri(CustomerController.CUSTOMER_PATH)
            .body(Mono.just(CustomerDTO.builder().name("").build()), CustomerDTO.class)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @Order(300)
    void testUpdateCustomer() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .put().uri(CustomerController.CUSTOMER_ID_PATH, 1)
            .body(Mono.just(CustomerDTO.builder().name("Spinner KG *UPDATED*").build()), CustomerDTO.class)
            .exchange()
            .expectStatus().isNoContent();
    }

    @Test
    @Order(300)
    void testUpdateCustomerWithValidationError() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .put().uri(CustomerController.CUSTOMER_ID_PATH, 1)
            .body(Mono.just(CustomerDTO.builder().name("").build()), CustomerDTO.class)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @Order(300)
    void testUpdateCustomerWithNonExistingId() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .put().uri(CustomerController.CUSTOMER_ID_PATH, 4711)
            .body(Mono.just(CustomerDTO.builder().name("Spinner KG *UPDATED*").build()), CustomerDTO.class)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(300)
    void testPatchCustomer() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .patch().uri(CustomerController.CUSTOMER_ID_PATH, 1)
            .body(Mono.just(CustomerDTO.builder().name("Spinner KG *PATCHED*").build()), CustomerDTO.class)
            .exchange()
            .expectStatus().isNoContent();
    }

    @Test
    @Order(999)
    void testDeleteCustomer() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .delete().uri(CustomerController.CUSTOMER_ID_PATH, 1)
            .exchange()
            .expectStatus().isNoContent();
    }

    @Test
    @Order(999)
    void testDeleteCustomerWithNonExistingId() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .delete().uri(CustomerController.CUSTOMER_ID_PATH, 4711)
            .exchange()
            .expectStatus().isNotFound();
    }

}