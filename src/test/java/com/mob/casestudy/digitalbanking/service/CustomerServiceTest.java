package com.mob.casestudy.digitalbanking.service;

import static com.mob.casestudy.digitalbanking.customerror.ErrorList.*;

import com.digitalbanking.openapi.model.CreateCustomerRequest;
import com.digitalbanking.openapi.model.GetCustomerResponse;
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

import java.util.ArrayList;
import java.util.List;
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
        Assertions.assertThrows(CustomNotFoundException.class, () -> customerService.findCustomerByUserName(name, CUS_DELETE_NOT_FOUND, "Invalid User.. " + name));
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
        String name = "Dipak";
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.setUserName(name);
        Mockito.when(customerRepo.existsByUserName(name)).thenReturn(true);
        Assertions.assertThrows(CustomBadRequestException.class, () -> customerService.createCustomer(createCustomerRequest));
    }

    @Test
    void createCustomer_withValidCustomerRequest_shouldCreateCustomer() {
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        Mockito.when(customerMapper.fromDto(createCustomerRequest)).thenReturn(customer);
        Mockito.when(customerRepo.save(customer)).thenReturn(customer);
        customerService.createCustomer(createCustomerRequest);
        Mockito.verify(validationService).validateAllField(createCustomerRequest);
        Mockito.verify(customerRepo, Mockito.times(1)).save(customer);
    }

    @Test
    void retrieveCustomer_withNullOrEmptyUseName_shouldReturnFindByIdCustomer() {
        UUID id = UUID.fromString("fa9c1974-0980-49cf-94f4-20aff1399d81");
        Customer customer = new Customer();
        GetCustomerResponse customerResponse = new GetCustomerResponse();
        Mockito.when(customerRepo.findById(id)).thenReturn(Optional.of(customer));
        Mockito.when(customerMapper.toDto(customer)).thenReturn(customerResponse);
        ResponseEntity<GetCustomerResponse> actual = customerService.retrieveCustomer("fa9c1974-0980-49cf-94f4-20aff1399d81", "");
        ResponseEntity<GetCustomerResponse> expected = ResponseEntity.ok(customerResponse);
        Mockito.verify(customerRepo).findById(id);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void retrieveCustomer_withNullOrEmptyId_shouldReturnFindByUserNameCustomer() {
        String name = "Dipak";
        Customer customer = new Customer();
        GetCustomerResponse customerResponse = new GetCustomerResponse();
        Mockito.when(customerRepo.findByUserName(name)).thenReturn(Optional.of(customer));
        Mockito.when(customerMapper.toDto(customer)).thenReturn(customerResponse);
        ResponseEntity<GetCustomerResponse> actual = customerService.retrieveCustomer(null, name);
        ResponseEntity<GetCustomerResponse> expected = ResponseEntity.ok(customerResponse);
        Mockito.verify(customerRepo).findByUserName(name);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void retrieveCustomer_withIdAndUserName_shouldReturnMatchedWithIdCustomer() {
        UUID id = UUID.fromString("fa9c1974-0980-49cf-94f4-20aff1399d81");
        String name = "Dipak";
        Customer customer = new Customer();
        GetCustomerResponse customerResponse = new GetCustomerResponse();
        customer.setId(id);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        Mockito.when(customerRepo.findByIdOrUserName(id, name)).thenReturn(customerList);
        Mockito.when(customerMapper.toDto(customer)).thenReturn(customerResponse);
        ResponseEntity<GetCustomerResponse> actual = customerService.retrieveCustomer("fa9c1974-0980-49cf-94f4-20aff1399d81", name);
        ResponseEntity<GetCustomerResponse> expected = ResponseEntity.ok(customerResponse);
        Mockito.verify(customerRepo).findByIdOrUserName(id, name);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void retrieveCustomer_withIdAndUserName_shouldReturnMatchedWithUserNameCustomer() {
        UUID id = UUID.fromString("fa9c1974-0980-49cf-94f4-20aff1399d81");
        String name = "Dipak";
        Customer customer = new Customer();
        GetCustomerResponse customerResponse = new GetCustomerResponse();
        customer.setId(UUID.randomUUID());
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        Mockito.when(customerRepo.findByIdOrUserName(id, name)).thenReturn(customerList);
        Mockito.when(customerMapper.toDto(customer)).thenReturn(customerResponse);
        ResponseEntity<GetCustomerResponse> actual = customerService.retrieveCustomer("fa9c1974-0980-49cf-94f4-20aff1399d81", name);
        ResponseEntity<GetCustomerResponse> expected = ResponseEntity.ok(customerResponse);
        Mockito.verify(customerRepo).findByIdOrUserName(id, name);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void retrieveCustomer_withNullOrEmptyIdAndNullOrEmptyUserName_shouldThrowException() {
        Assertions.assertThrows(CustomBadRequestException.class, () -> customerService.retrieveCustomer("", null));
    }

    @Test
    void retrieveCustomer_withEmptyCustomerList_shouldThrowException() {
        String id = "fa9c1974-0980-49cf-94f4-20aff1399d81";
        String name = "Dipak";
        List<Customer> customerList = new ArrayList<>();
        Mockito.when(customerRepo.findByIdOrUserName(UUID.fromString(id), name)).thenReturn(customerList);
        Assertions.assertThrows(CustomNotFoundException.class, () -> customerService.retrieveCustomer(id, name));
    }

    @Test
    void findCustomerById_withInvalidId_shouldThrowException() {
        UUID id = UUID.fromString("fa9c1974-0980-49cf-94f4-20aff1399d81");
        Mockito.when(customerRepo.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(CustomNotFoundException.class, () -> customerService.findCustomerById(id, CUS_NOT_FOUND_ERROR, CUS_NOT_FOUND_DESCRIPTION));
    }

    @Test
    void findCustomerById_withValidId_shouldReturnCustomer() {
        UUID id = UUID.fromString("fa9c1974-0980-49cf-94f4-20aff1399d81");
        Customer customer = new Customer();
        Mockito.when(customerRepo.findById(id)).thenReturn(Optional.of(customer));
        customerService.findCustomerById(id, CUS_NOT_FOUND_ERROR, CUS_NOT_FOUND_DESCRIPTION);
        Mockito.verify(customerRepo).findById(id);
    }
}











