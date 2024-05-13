package com.jovisco.spring6reactive.services;

import com.jovisco.spring6reactive.model.BeerDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {

    Flux<BeerDTO> getAllBeers();
    Mono<BeerDTO> getBeerById(Integer id);
    Mono<BeerDTO> createBeer(BeerDTO beerDto);
    Mono<BeerDTO> updateBeer(Integer id, BeerDTO beerDto);
    Mono<BeerDTO> patchBeer(Integer id, BeerDTO beerDto);
    Mono<Object> deleteBeer(Integer id);

}
