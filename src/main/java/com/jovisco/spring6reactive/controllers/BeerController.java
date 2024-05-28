package com.jovisco.spring6reactive.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.jovisco.spring6reactive.model.BeerDTO;
import com.jovisco.spring6reactive.services.BeerService;

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
public class BeerController {

    public static final String BEER_PATH = "/api/v2/beers";
    public static final String BEER_ID_PATH = BEER_PATH + "/{id}";

    private final BeerService beerService;
    
    @GetMapping(BEER_PATH)
    Flux<BeerDTO> getAllBeers() {
        return beerService.getAllBeers();
    }

    @GetMapping(BEER_ID_PATH)
    Mono<BeerDTO> getBeerById(@PathVariable Integer id) {
        return beerService.getBeerById(id);
    }

    @PostMapping(BEER_PATH)
    Mono<ResponseEntity<Void>> createBeer(@Validated @RequestBody BeerDTO beerDto) {
        return beerService.createBeer(beerDto)
            .map(created -> {
                // add the Location header
                return ResponseEntity.created(
                    UriComponentsBuilder.fromHttpUrl("http://localhost:8080/" + BEER_PATH + "/" + created.getId())
                        .build()
                        .toUri()
                )
                .build();
            });
    }

    @PutMapping(BEER_ID_PATH)
    Mono<ResponseEntity<Void>> updateBeer(@PathVariable Integer id, @Validated @RequestBody BeerDTO beerDto) {
        return beerService.updateBeer(id, beerDto)
            .map(updated -> ResponseEntity.noContent().build());
    }

    @PatchMapping(BEER_ID_PATH)
    Mono<ResponseEntity<Void>> patchBeer(@PathVariable Integer id, @Validated @RequestBody BeerDTO beerDto) {
        return beerService.patchBeer(id, beerDto)
            .map(updated -> ResponseEntity.noContent().build());
    }

    @DeleteMapping(BEER_ID_PATH)
    Mono<ResponseEntity<Void>> deleteBeer(@PathVariable Integer id) {
        return beerService.deleteBeer(id)
            .thenReturn(ResponseEntity.noContent().build());
    }

}
