package com.mob.casestudy.digitalbanking.service;

import static com.mob.casestudy.digitalbanking.customerror.ErrorList.*;

import com.digitalbanking.openapi.model.CreateCustomerRequest;
import com.digitalbanking.openapi.model.CreateCustomerResponse;
import com.mob.casestudy.digitalbanking.dto.CustomerAge;
import com.mob.casestudy.digitalbanking.exception.*;
import com.mob.casestudy.digitalbanking.mapper.CustomerMapperImpl;
import com.mob.casestudy.digitalbanking.repository.CustomerRepo;
import com.mob.casestudy.digitalbanking.entity.*;
import com.mob.casestudy.digitalbanking.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    ValidationService validationService;

    @Autowired
    CustomerMapperImpl customerMapper;

    @Transactional
    public ResponseEntity<Void> deleteCustomer(String userName) {
        Customer customer = findCustomerByUserName(userName, CUS_DELETE_NOT_FOUND, "Invalid User.. " + userName);
        customerRepo.delete(customer);
        return ResponseEntity.noContent().build();
    }

    public Customer findCustomerByUserName(String userName, String errorCode, String errorDescription) {
        return customerRepo.findByUserName(userName).orElseThrow(() -> new CustomNotFoundException(errorCode, errorDescription));
    }

    @Transactional
    public ResponseEntity<CreateCustomerResponse> createCustomer(CreateCustomerRequest createCustomerRequest) {

        validationService.validateAllField(createCustomerRequest);
        checkUserName(createCustomerRequest);
        String age = checkAge();
        Customer customer = customerMapper.fromDto(createCustomerRequest);
        customer.setAge(age);
        return ResponseEntity.ok().body(new CreateCustomerResponse().id(customerRepo.save(customer).getId().toString()));
    }

    private void checkUserName(CreateCustomerRequest createCustomerRequest) {
        if (customerRepo.existsByUserName(createCustomerRequest.getUserName())) {
            throw new CustomBadRequestException(USER_NAME_NOT_UNIQUE_ERROR, "This user is already in the table");
        }
    }

    private String checkAge() {
        RestTemplate restTemplate = new RestTemplate();
        CustomerAge customerAge = restTemplate.getForObject("https://api.agify.io/?name=jack", CustomerAge.class);
        return Objects.requireNonNull(customerAge).getAge();
    }
}
