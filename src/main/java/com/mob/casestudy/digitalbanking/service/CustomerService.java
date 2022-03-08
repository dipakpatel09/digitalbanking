package com.mob.casestudy.digitalbanking.service;

import static com.mob.casestudy.digitalbanking.customerror.ErrorList.*;

import com.digitalbanking.openapi.model.CreateCustomerRequest;
import com.digitalbanking.openapi.model.CreateCustomerResponse;
import com.digitalbanking.openapi.model.GetCustomerResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
        Customer customer = customerMapper.fromDto(createCustomerRequest);
        String age = checkAge(customer);
        customer.setAge(age);
        return ResponseEntity.ok().body(new CreateCustomerResponse().id(customerRepo.save(customer).getId().toString()));
    }

    private void checkUserName(CreateCustomerRequest createCustomerRequest) {
        if (customerRepo.existsByUserName(createCustomerRequest.getUserName())) {
            throw new CustomBadRequestException(USER_NAME_NOT_UNIQUE_ERROR, "This user is already in the table");
        }
    }

    private String checkAge(Customer customer) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();
        String age = null;
        try {
            JsonNode jsonNode = objectMapper.readTree(restTemplate.getForEntity("https://api.agify.io/?name=" + customer.getUserName(), String.class).getBody());
            age = String.valueOf(jsonNode.path("age"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return age;
    }

    @Transactional
    public ResponseEntity<GetCustomerResponse> retrieveCustomer(String id, String userName) {
        if ((Objects.isNull(id) || id.isEmpty()) && (Objects.isNull(userName) || userName.isEmpty())) {
            throw new CustomBadRequestException(CUS_FIELD_VALIDATION_ERROR, "Customer field validation failed");
        }
        Customer customer = findCustomer(id, userName);
        return ResponseEntity.ok(customerMapper.toDto(customer));
    }

    public Customer findCustomerById(UUID id, String errorCode, String errorDescription) {
        return customerRepo.findById(id).orElseThrow(() -> new CustomNotFoundException(errorCode, errorDescription));
    }

    private Customer findCustomerByIdOrUserName(String id, String userName) {
        List<Customer> customerList = customerRepo.findByIdOrUserName(UUID.fromString(id), userName);
        if (customerList.isEmpty()) {
            throw new CustomNotFoundException(CUS_NOT_FOUND_ERROR, CUS_NOT_FOUND_DESCRIPTION);
        }
        for (int i = 0; i < customerList.size(); i++) {
            if (customerList.get(i).getId().equals(UUID.fromString(id))) {
                return customerList.get(i);
            }
        }
        return customerList.get(0);
    }

    private Customer findCustomer(String id, String userName) {
        if (userName == null || userName.isEmpty()) {
            return findCustomerById(UUID.fromString(id), CUS_NOT_FOUND_ERROR, CUS_NOT_FOUND_DESCRIPTION);
        } else if (id == null || id.isEmpty()) {
            return findCustomerByUserName(userName, CUS_NOT_FOUND_ERROR, CUS_NOT_FOUND_DESCRIPTION);
        } else {
            return findCustomerByIdOrUserName(id, userName);
        }
    }
}
