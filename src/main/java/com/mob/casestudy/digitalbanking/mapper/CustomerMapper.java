package com.mob.casestudy.digitalbanking.mapper;

import com.digitalbanking.openapi.model.CreateCustomerRequest;
import com.digitalbanking.openapi.model.GetCustomerResponse;
import com.mob.casestudy.digitalbanking.entity.Customer;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer fromDto(CreateCustomerRequest createCustomerRequest);

    GetCustomerResponse toDto(Customer customer);

    default String map(UUID id) {
        return id.toString();
    }

}
