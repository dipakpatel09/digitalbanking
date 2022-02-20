package com.mob.casestudy.digitalbanking.service;

import com.digitalbanking.openapi.model.CreateCustomerSecurityQuestionsRequest;
import com.digitalbanking.openapi.model.SecurityQuestion;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityQuestions;
import com.mob.casestudy.digitalbanking.entity.SecurityQuestions;
import com.mob.casestudy.digitalbanking.mapper.CustomerSecurityQuestionsMapperImpl;
import com.mob.casestudy.digitalbanking.repository.CustomerSecurityQuestionsRepo;
import com.mob.casestudy.digitalbanking.validation.ValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mob.casestudy.digitalbanking.customerror.ErrorList.CUS_SEC_QUES_CUS_NOT_FOUND;

@ExtendWith(MockitoExtension.class)
class CustomerSecurityQuestionsServiceTest {

    @Mock
    CustomerSecurityQuestionsRepo customerSecurityQuestionsRepo;

    @InjectMocks
    CustomerSecurityQuestionsService customerSecurityQuestionsService;

    @Mock
    CustomerService customerService;

    @Mock
    SecurityQuestionsService securityQuestionsService;

    @Mock
    ValidationService validationService;

    @Mock
    CustomerSecurityQuestionsMapperImpl customerSecurityQuestionsMapper;

    @Test
    void createSecurityQuestions_withValidSecurityQuestions_shouldCreateCustomerSecurityQuestions() {
        String name = "Dipak";
        UUID id = UUID.randomUUID();
        Customer customer = new Customer();
        CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest = new CreateCustomerSecurityQuestionsRequest();
        List<SecurityQuestions> secQuesList = new ArrayList<>();
        SecurityQuestion securityQuestionsDto = new SecurityQuestion().securityQuestionId(id.toString()).securityQuestionAnswer("Red");
        List<SecurityQuestion> securityQuestionList = List.of(securityQuestionsDto, securityQuestionsDto, securityQuestionsDto);
        SecurityQuestions securityQuestions = new SecurityQuestions();
        securityQuestions.setId(id);

        createCustomerSecurityQuestionsRequest.setSecurityQuestions(securityQuestionList);

        Mockito.when(customerService.findCustomerByUserName(name, CUS_SEC_QUES_CUS_NOT_FOUND, "The requested user not found.. " + name)).thenReturn(customer);
        Mockito.when(validationService.validateQuestionId(id, secQuesList)).thenReturn(securityQuestions);
        Mockito.when(securityQuestionsService.findAllSecurityQuestion()).thenReturn(secQuesList);
        Mockito.when(customerSecurityQuestionsMapper.fromDto(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any())).thenReturn(new CustomerSecurityQuestions());
        customerSecurityQuestionsService.createSecurityQuestions(name, createCustomerSecurityQuestionsRequest);

        Mockito.verify(validationService).validateSecurityQuestion(createCustomerSecurityQuestionsRequest);
        Mockito.verify(validationService).validateSecurityQuestionSize(createCustomerSecurityQuestionsRequest);
        Mockito.verify(validationService).validateSecurityQuestionAnswer(createCustomerSecurityQuestionsRequest);
        Mockito.verify(customerSecurityQuestionsRepo, Mockito.times(3)).save(Mockito.any());
    }

    @Test
    void deleteCustomerQuestion_withEmptyCustomerQuestion_shouldNotDeleteAnything() {
        Customer customer = new Customer();
        customerSecurityQuestionsService.deleteCustomerQuestion(customer);
        Mockito.verify(customerSecurityQuestionsRepo, Mockito.times(0)).deleteAll();
    }

    @Test
    void deleteCustomerQuestion_withValidCustomerQuestion_shouldDeleteExistingCustomerQuestion() {
        Customer customer = new Customer();
        customer.addCustomerSecurityQuestions(new CustomerSecurityQuestions("Ahmedabad", LocalDateTime.now()));
        customerSecurityQuestionsService.deleteCustomerQuestion(customer);
        Mockito.verify(customerSecurityQuestionsRepo).deleteAll();
    }
}