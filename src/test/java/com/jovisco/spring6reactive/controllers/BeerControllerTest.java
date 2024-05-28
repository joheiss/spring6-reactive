
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

import com.jovisco.spring6reactive.model.BeerDTO;
import com.jovisco.spring6reactive.repositories.BeerRepositoryTest;

import reactor.core.publisher.Mono;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BeerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(100)
    void testGetAll() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .get().uri(BeerController.BEER_PATH)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Test
    @Order(100)
    void testGetById() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .get().uri(BeerController.BEER_ID_PATH, 1)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody(BeerDTO.class);
    }

    @Test
    @Order(100)
    void testGetByIdNotFound() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .get().uri(BeerController.BEER_ID_PATH, 4711)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(200)
    void testCreateBeer() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .post().uri(BeerController.BEER_PATH)
            .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().exists("Location");
    }

    @Test
    @Order(200)
    void testCreateBeerWithValidationError() {
        var beer = BeerRepositoryTest.getTestBeer();
        beer.setBeerName("A");

        webTestClient
            .mutateWith(mockOAuth2Login())
            .post().uri(BeerController.BEER_PATH)
            .body(Mono.just(beer), BeerDTO.class)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @Order(300)
    void testUpdateBeer() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .put().uri(BeerController.BEER_ID_PATH, 1)
            .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
            .exchange()
            .expectStatus().isNoContent();
    }

    @Test
    @Order(300)
    void testUpdateBeerWithValidationError() {
        var beer = BeerRepositoryTest.getTestBeer();
        beer.setBeerName("A");

        webTestClient
            .mutateWith(mockOAuth2Login())
            .put().uri(BeerController.BEER_ID_PATH, 1)
            .body(Mono.just(beer), BeerDTO.class)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @Order(300)
    void testUpdateBeerWithNonExistingId() {
        var beer = BeerRepositoryTest.getTestBeer();

        webTestClient
            .mutateWith(mockOAuth2Login())
            .put().uri(BeerController.BEER_ID_PATH, 4711)
            .body(Mono.just(beer), BeerDTO.class)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(300)
    void testPatchBeer() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .patch().uri(BeerController.BEER_ID_PATH, 1)
            .body(Mono.just(BeerDTO.builder().beerName("**UPDATED**").beerStyle("SUGAR").build()), BeerDTO.class)
            .exchange()
            .expectStatus().isNoContent();
    }

    @Test
    @Order(999)
    void testDeleteBeer() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .delete().uri(BeerController.BEER_ID_PATH, 1)
            .exchange()
            .expectStatus().isNoContent();
    }

    @Test
    @Order(999)
    void testDeleteBeerNotFound() {
        webTestClient
            .mutateWith(mockOAuth2Login())
            .delete().uri(BeerController.BEER_ID_PATH, 4711)
            .exchange()
            .expectStatus().isNotFound();
    }

}
