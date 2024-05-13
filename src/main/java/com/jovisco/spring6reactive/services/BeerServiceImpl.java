package com.jovisco.spring6reactive.services;

import org.springframework.stereotype.Service;

import com.jovisco.spring6reactive.controllers.NotFoundException;
import com.jovisco.spring6reactive.mappers.BeerMapper;
import com.jovisco.spring6reactive.model.BeerDTO;
import com.jovisco.spring6reactive.repositories.BeerRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public Flux<BeerDTO> getAllBeers() {

        return beerRepository.findAll()
            .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> getBeerById(Integer id) {
        return beerRepository.findById(id)
            .map(beerMapper::beerToBeerDto)
            .switchIfEmpty(Mono.error(new NotFoundException("The data you seek is not here.")));
    }

    @Override
    public Mono<BeerDTO> createBeer(BeerDTO beerDto) {
        var beer = beerMapper.beerDtoToBeer(beerDto);
        return beerRepository.save(beer)
            .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> updateBeer(Integer id, BeerDTO beerDto) {
        return beerRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("No data found for id: " + id)))
            .map(beer -> {
                beer.setBeerName(beerDto.getBeerName());
                beer.setBeerStyle(beerDto.getBeerStyle());
                beer.setPrice(beerDto.getPrice());
                beer.setQuantityOnHand(beerDto.getQuantityOnHand());
                beer.setUpc(beerDto.getUpc());
                return beer;
            })
            .flatMap(beerRepository::save)
            .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> patchBeer(Integer id, BeerDTO beerDto) {
        return beerRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("No data found for id: " + id)))
            .map(beer -> {
                if (beerDto.getBeerName() != null) beer.setBeerName(beerDto.getBeerName());
                if (beerDto.getBeerStyle() != null) beer.setBeerStyle(beerDto.getBeerStyle());
                if (beerDto.getPrice() != null) beer.setPrice(beerDto.getPrice());
                if (beerDto.getQuantityOnHand() != null) beer.setQuantityOnHand(beerDto.getQuantityOnHand());
                if (beerDto.getUpc() != null) beer.setUpc(beerDto.getUpc());
                return beer;
            })
            .flatMap(beerRepository::save)
            .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<Object> deleteBeer(Integer id) {
        return beerRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException("No data found for id: " + id)))
            .map(beer ->beer.getId())
            .flatMap(beerRepository::deleteById);
    }

}
