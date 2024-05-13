package com.jovisco.spring6reactive.bootstrap;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.jovisco.spring6reactive.domain.Beer;
import com.jovisco.spring6reactive.domain.Customer;
import com.jovisco.spring6reactive.repositories.BeerRepository;
import com.jovisco.spring6reactive.repositories.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {

        beerRepository
            .count()
            .subscribe(count -> {
                log.info("Beers on database: " + count);
                if (count > 0) return;
                loadBeers();
            });

        customerRepository
            .count()
            .subscribe(count -> {
                log.info("Customers on database: " + count);
                if (count > 0) return;
                loadCustomers();
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

    private void loadCustomers() {
        customerRepository
            .saveAll(getCustomers())
            .subscribe(
                customer -> log.info("Data loaded successfully: " + customer.getName()),
                error -> log.error("Error while loading data", error)
            );
    }

    private List<Customer> getCustomers() {

        Customer customer1 = Customer.builder().name("Hampelmann AG").build();
        Customer customer2 = Customer.builder().name("Irrweg GmbH").build();
        Customer customer3 = Customer.builder().name("Wurstl KG").build();
        Customer customer4 = Customer.builder().name("Zehnerl OHG").build();

        return Arrays.asList(customer1, customer2, customer3, customer4);
    }

}
