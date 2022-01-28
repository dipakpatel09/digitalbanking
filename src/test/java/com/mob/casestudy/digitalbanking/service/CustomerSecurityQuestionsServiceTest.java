package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.repository.CustomerSecurityQuestionsRepo;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityQuestions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class CustomerSecurityQuestionsServiceTest {

    @InjectMocks
    CustomerSecurityQuestionsService customerSecurityQuestionsService;

    @Mock
    CustomerSecurityQuestionsRepo customerSecurityQuestionsRepo;

    @Test
    void deleteCustomerSecurityQuestions_withValidList_shouldDeleteQuestions() {

        List<CustomerSecurityQuestions> questions=new ArrayList<>();
        customerSecurityQuestionsService.deleteCustomerSecurityQuestions(questions);
        Mockito.verify(customerSecurityQuestionsRepo).deleteAll(questions);

    }
}