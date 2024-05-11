package com.jovisco.spring6reactive.bootstrap;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.jovisco.spring6reactive.domain.Beer;
import com.jovisco.spring6reactive.repositories.BeerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {

        beerRepository
            .count()
            .subscribe(count -> {
                log.info("Beers on database: " + count);
                if (count > 0) return;
                loadBeers();
            });
    }

    private void loadBeers() {
        beerRepository
            .saveAll(getBeers())
            .subscribe(
                beer -> log.info("Data loaded successfully: " + beer.getBeerName()),
                error -> log.error("Error while loading data", error)
            );
    }

    private List<Beer> getBeers() {

        Beer beer1 = Beer.builder()
            .beerName("Erdinger Weizen")
            .beerStyle("WHEAT")
            .upc("12341")
            .price(new BigDecimal("9.87"))
            .quantityOnHand(121)
            .build();

        Beer beer2 = Beer.builder()
            .beerName("Salvator Bock")
            .beerStyle("STOUT")
            .upc("12342")
            .price(new BigDecimal("1.23"))
            .quantityOnHand(122)
            .build();

        Beer beer3 = Beer.builder()
            .beerName("Paulaner Hell")
            .beerStyle("PALE_ALE")
            .upc("12343")
            .price(new BigDecimal("12.34"))
            .quantityOnHand(123)
            .build();

        return Arrays.asList(beer1, beer2, beer3);
    }
}
