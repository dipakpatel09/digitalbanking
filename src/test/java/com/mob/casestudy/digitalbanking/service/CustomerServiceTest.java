package com.mob.casestudy.digitalbanking.service;

import static com.mob.casestudy.digitalbanking.customerror.ErrorList.*;

import com.digitalbanking.openapi.model.CreateCustomerRequest;
import com.mob.casestudy.digitalbanking.exception.CustomBadRequestException;
import com.mob.casestudy.digitalbanking.exception.CustomNotFoundException;
import com.mob.casestudy.digitalbanking.mapper.CustomerMapperImpl;
import com.mob.casestudy.digitalbanking.repository.CustomerRepo;
import com.mob.casestudy.digitalbanking.entity.*;
import com.mob.casestudy.digitalbanking.validation.ValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepo customerRepo;

    @Mock
    ValidationService validationService;

    @Mock
    CustomerMapperImpl customerMapper;

    @Test
    void deleteCustomer_withValidCustomer_shouldDeleteCustomer() {
        String name = "Dipak";
        Customer customer = new Customer();
        Mockito.when(customerRepo.findByUserName(name)).thenReturn(Optional.of(customer));
        ResponseEntity<Void> actual = customerService.deleteCustomer(name);
        ResponseEntity<Object> expected = ResponseEntity.noContent().build();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findCustomerByUserName_withEmptyCustomer_shouldThrowException() {
        String name = "Dipak";
        Mockito.when(customerRepo.findByUserName(name)).thenReturn(Optional.empty());
        Assertions.assertThrows(CustomNotFoundException.class, () -> customerService.deleteCustomer(name));
    }

    @Test
    void findCustomerByUserName_withValidCustomer_shouldReturnCustomer() {
        String userName = "Dipak";
        Customer customer = new Customer();
        Mockito.when(customerRepo.findByUserName(userName)).thenReturn(Optional.of(customer));
        customerService.findCustomerByUserName(userName, CUS_DELETE_NOT_FOUND, "Invalid User.. " + userName);
        Mockito.verify(customerRepo).findByUserName(userName);
    }

    @Test
    void createCustomer_withExistingCustomer_shouldThrowException() {
        String name="Dipak";
        CreateCustomerRequest createCustomerRequest=new CreateCustomerRequest();
        createCustomerRequest.setUserName(name);
        Mockito.when(customerRepo.existsByUserName(name)).thenReturn(true);
        Assertions.assertThrows(CustomBadRequestException.class,()->customerService.createCustomer(createCustomerRequest));
    }

    @Test
    void createCustomer_withValidCustomerRequest_shouldCreateCustomer() {
        CreateCustomerRequest createCustomerRequest=new CreateCustomerRequest();
        Customer customer=new Customer();
        customer.setId(UUID.randomUUID());
        Mockito.when(customerMapper.fromDto(createCustomerRequest)).thenReturn(customer);
        Mockito.when(customerRepo.save(customer)).thenReturn(customer);
        customerService.createCustomer(createCustomerRequest);
        Mockito.verify(validationService).validateAllField(createCustomerRequest);
        Mockito.verify(customerRepo,Mockito.times(1)).save(customer);
    }
}











