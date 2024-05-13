package com.jovisco.spring6reactive.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.jovisco.spring6reactive.domain.Customer;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {}
