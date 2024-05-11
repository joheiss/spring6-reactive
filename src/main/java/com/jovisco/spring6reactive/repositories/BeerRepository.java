package com.jovisco.spring6reactive.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.jovisco.spring6reactive.domain.Beer;

public interface BeerRepository extends ReactiveCrudRepository<Beer, Integer> {

}
