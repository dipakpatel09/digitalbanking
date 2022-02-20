package com.mob.casestudy.digitalbanking.mapper;

import com.digitalbanking.openapi.model.CreateCustomerRequest;
import com.mob.casestudy.digitalbanking.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer fromDto(CreateCustomerRequest createCustomerRequest);
}
