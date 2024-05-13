package com.jovisco.spring6reactive.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.jovisco.spring6reactive.domain.Customer;
import com.jovisco.spring6reactive.model.CustomerDTO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {
    Customer dtoToCustomer(CustomerDTO customerDTO);
    CustomerDTO customerToDto(Customer customer);
}
