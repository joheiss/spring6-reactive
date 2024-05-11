package com.jovisco.spring6reactive.repositories;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import com.jovisco.spring6reactive.config.DatabaseConfig;
import com.jovisco.spring6reactive.domain.Beer;

@DataR2dbcTest
@Import(DatabaseConfig.class)
public class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void createBeer() {
        beerRepository.save(getTestBeer())
            .subscribe(System.out::println);
    }

    Beer getTestBeer() {
        return Beer.builder()
            .beerName("Paulaner Hell")
            .beerStyle("PALE ALE")
            .price(new BigDecimal("9.87"))
            .quantityOnHand(23)
            .upc("1234567")
            .build();
    }
}
