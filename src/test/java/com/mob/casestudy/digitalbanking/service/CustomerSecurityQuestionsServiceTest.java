package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityQuestions;
import com.mob.casestudy.digitalbanking.repository.CustomerSecurityQuestionsRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class CustomerSecurityQuestionsServiceTest {

    @Mock
    CustomerSecurityQuestionsRepo customerSecurityQuestionsRepo;

    @InjectMocks
    CustomerSecurityQuestionsService customerSecurityQuestionsService;

    @Test
    void deleteCustomerQuestion_withEmptyCustomerQuestion_shouldThrowException() {
        Customer customer=new Customer();
        customerSecurityQuestionsService.deleteCustomerQuestion(customer);
        Mockito.verify(customerSecurityQuestionsRepo,Mockito.times(0)).deleteAll();
    }

    @Test
    void deleteCustomerQuestion_withValidCustomerQuestion_shouldDeleteExistingCustomerQuestion() {
        Customer customer=new Customer();
        customer.addCustomerSecurityQuestions(new CustomerSecurityQuestions("Ahmedabad", LocalDateTime.now()));
        customerSecurityQuestionsService.deleteCustomerQuestion(customer);
        Mockito.verify(customerSecurityQuestionsRepo).deleteAll();
    }
}