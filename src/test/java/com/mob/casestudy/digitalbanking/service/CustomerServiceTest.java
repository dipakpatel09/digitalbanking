package com.mob.casestudy.digitalbanking.service;

import static com.mob.casestudy.digitalbanking.errorlist.CustomError.*;

import com.mob.casestudy.digitalbanking.dto.SecurityQuestionsDto;
import com.mob.casestudy.digitalbanking.dto.CreateCustomerSecurityQuestionsRequest;
import com.mob.casestudy.digitalbanking.exception.CustomNotFoundException;
import com.mob.casestudy.digitalbanking.repository.CustomerRepo;
import com.mob.casestudy.digitalbanking.entity.*;
import com.mob.casestudy.digitalbanking.repository.CustomerSecurityQuestionsRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
    SecurityQuestionsService securityQuestionsService;

    @Mock
    CustomerSecurityQuestionsService customerSecurityQuestionsService;

    @Mock
    CustomerSecurityQuestionsRepo customerSecurityQuestionsRepo;

    @Test
    void deleteCustomer_withValidCustomer_shouldDeleteCustomer() {
        String name = "Dipak";
        Customer customer = new Customer();
        Mockito.when(customerRepo.findByUserName(name)).thenReturn(Optional.of(customer));
        customerService.deleteCustomer(name);
        Mockito.verify(customerRepo).delete(customer);
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
    void createSecurityQuestions_withValidSecurityQuestions_shouldCreateCustomerSecurityQuestions() {
        String name = "Dipak";
        UUID id = UUID.randomUUID();
        CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest = new CreateCustomerSecurityQuestionsRequest();
        List<SecurityQuestionsDto> securityQuestionsList = new ArrayList<>();
        SecurityQuestionsDto securityQuestionsDto = new SecurityQuestionsDto();
        SecurityQuestions securityQuestions = new SecurityQuestions();
        Customer customer = new Customer();

        securityQuestions.setId(id);
        securityQuestionsDto.setSecurityQuestionId(id.toString());
        securityQuestionsDto.setSecurityQuestionAnswer("Red");
        securityQuestionsList.add(securityQuestionsDto);
        securityQuestionsList.add(securityQuestionsDto);
        securityQuestionsList.add(securityQuestionsDto);
        createCustomerSecurityQuestionsRequest.setSecurityQuestions(securityQuestionsList);

        Mockito.when(customerRepo.findByUserName(name)).thenReturn(Optional.of(customer));
        Mockito.when(securityQuestionsService.validateQuestionId(id)).thenReturn(securityQuestions);
        customerService.createSecurityQuestions(name, createCustomerSecurityQuestionsRequest);

        Mockito.verify(securityQuestionsService).validateSecurityQuestion(createCustomerSecurityQuestionsRequest);
        Mockito.verify(securityQuestionsService).validateSecurityQuestionSize(createCustomerSecurityQuestionsRequest);
        Mockito.verify(customerSecurityQuestionsService).deleteCustomerQuestion(customer);
        Mockito.verify(customerSecurityQuestionsRepo, Mockito.times(3)).save(Mockito.any());
    }
}











