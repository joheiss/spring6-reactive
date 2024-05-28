package com.jovisco.spring6reactive.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.jovisco.spring6reactive.model.CustomerDTO;
import com.jovisco.spring6reactive.services.CustomerService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v2/customers";
    public static final String CUSTOMER_ID_PATH = CUSTOMER_PATH + "/{id}";

    private final CustomerService CustomerService;
    
    @GetMapping(CUSTOMER_PATH)
    Flux<CustomerDTO> getAllCustomers() {
        return CustomerService.getAllCustomers();
    }

    @GetMapping(CUSTOMER_ID_PATH)
    Mono<CustomerDTO> getCustomerById(@PathVariable Integer id) {
        return CustomerService.getCustomerById(id);
    }

    @PostMapping(CUSTOMER_PATH)
    Mono<ResponseEntity<Void>> createCustomer(@Validated @RequestBody CustomerDTO CustomerDto) {
        return CustomerService.createCustomer(CustomerDto)
            .map(created -> {
                // add the Location header
                return ResponseEntity.created(
                    UriComponentsBuilder.fromHttpUrl("http://localhost:8080/" + CUSTOMER_PATH + "/" + created.getId())
                        .build()
                        .toUri()
                )
                .build();
            });
    }

    @PutMapping(CUSTOMER_ID_PATH)
    Mono<ResponseEntity<Void>> updateCustomer(@PathVariable Integer id, @Validated @RequestBody CustomerDTO CustomerDto) {
        return CustomerService.updateCustomer(id, CustomerDto)
            .map(updated -> ResponseEntity.noContent().build());
    }

    @PatchMapping(CUSTOMER_ID_PATH)
    Mono<ResponseEntity<Void>> patchCustomer(@PathVariable Integer id, @Validated @RequestBody CustomerDTO CustomerDto) {
        return CustomerService.patchCustomer(id, CustomerDto)
            .map(updated -> ResponseEntity.noContent().build());
    }

    @DeleteMapping(CUSTOMER_ID_PATH)
    Mono<ResponseEntity<Void>> deleteCustomer(@PathVariable Integer id) {
        return CustomerService.deleteCustomer(id)
            .thenReturn(ResponseEntity.noContent().build());    }

}
