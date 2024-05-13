package com.jovisco.spring6reactive.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.jovisco.spring6reactive.domain.Beer;
import com.jovisco.spring6reactive.model.BeerDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BeerMapper {
    Beer beerDtoToBeer(BeerDTO beerDTO);
    BeerDTO beerToBeerDto(Beer beer);
}
