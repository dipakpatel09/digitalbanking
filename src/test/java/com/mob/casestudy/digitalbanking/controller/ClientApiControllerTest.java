package com.mob.casestudy.digitalbanking.controller;

import com.digitalbanking.openapi.model.CreateCustomerSecurityQuestionsRequest;
import com.mob.casestudy.digitalbanking.service.CustomerSecurityImagesService;
import com.mob.casestudy.digitalbanking.service.CustomerSecurityQuestionsService;
import com.mob.casestudy.digitalbanking.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClientApiControllerTest {

    @InjectMocks
    ClientApiController clientApiController;

    @Mock
    CustomerService customerService;

    @Mock
    CustomerSecurityImagesService customerSecurityImagesService;

    @Mock
    CustomerSecurityQuestionsService customerSecurityQuestionsService;

    @Test
    void deleteCustomerByUserName() {
        String name = "Dipak";
        clientApiController.deleteCustomerByUserName(name);
        Mockito.verify(customerService).deleteCustomer(name);
    }

    @Test
    void getSecurityImageByUserName() {
        String name = "Dipak";
        clientApiController.getSecurityImageByUserName(name);
        Mockito.verify(customerSecurityImagesService).retrieveSecurityImageByUserName(name);
    }

    @Test
    void createSecurityQuestionsByUserName() {
        String name = "Dipak";
        CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest = new CreateCustomerSecurityQuestionsRequest();
        clientApiController.createSecurityQuestionsByUserName(name, createCustomerSecurityQuestionsRequest);
        Mockito.verify(customerSecurityQuestionsService).createSecurityQuestions(name, createCustomerSecurityQuestionsRequest);
    }
}