package com.jovisco.spring6reactive.services;

import com.jovisco.spring6reactive.model.CustomerDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Flux<CustomerDTO> getAllCustomers();
    Mono<CustomerDTO> getCustomerById(Integer id);
    Mono<CustomerDTO> createCustomer(CustomerDTO CustomerDto);
    Mono<CustomerDTO> updateCustomer(Integer id, CustomerDTO CustomerDto);
    Mono<CustomerDTO> patchCustomer(Integer id, CustomerDTO CustomerDto);
    Mono<Object> deleteCustomer(Integer id);

}
