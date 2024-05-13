package com.jovisco.spring6reactive.services;

import org.springframework.stereotype.Service;

import com.jovisco.spring6reactive.controllers.NotFoundException;
import com.jovisco.spring6reactive.mappers.CustomerMapper;
import com.jovisco.spring6reactive.model.CustomerDTO;
import com.jovisco.spring6reactive.repositories.CustomerRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository CustomerRepository;
    private final CustomerMapper CustomerMapper;

    @Override
    public Flux<CustomerDTO> getAllCustomers() {

        return CustomerRepository.findAll()
            .map(CustomerMapper::customerToDto);
    }

    @Override
    public Mono<CustomerDTO> getCustomerById(Integer id) {
        return CustomerRepository.findById(id)
            .map(CustomerMapper::customerToDto)
            .switchIfEmpty(Mono.error(new NotFoundException("The data you seek is not here.")));
    }

    @Override
    public Mono<CustomerDTO> createCustomer(CustomerDTO CustomerDto) {
        var Customer = CustomerMapper.dtoToCustomer(CustomerDto);
        return CustomerRepository.save(Customer)
            .map(CustomerMapper::customerToDto);
    }

    @Override
    public Mono<CustomerDTO> updateCustomer(Integer id, CustomerDTO CustomerDto) {
        return CustomerRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("No data found for id: " + id)))
            .map(Customer -> {
                Customer.setName(CustomerDto.getName());
                return Customer;
            })
            .flatMap(CustomerRepository::save)
            .map(CustomerMapper::customerToDto);
    }

    @Override
    public Mono<CustomerDTO> patchCustomer(Integer id, CustomerDTO CustomerDto) {
        return CustomerRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("No data found for id: " + id)))
            .map(Customer -> {
                if (CustomerDto.getName() != null) Customer.setName(CustomerDto.getName());
                return Customer;
            })
            .flatMap(CustomerRepository::save)
            .map(CustomerMapper::customerToDto);
    }

    @Override
    public Mono<Object> deleteCustomer(Integer id) {
        return CustomerRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("No data found for id: " + id)))
            .map(Customer ->Customer.getId())
            .flatMap(CustomerRepository::deleteById);
    }

}
