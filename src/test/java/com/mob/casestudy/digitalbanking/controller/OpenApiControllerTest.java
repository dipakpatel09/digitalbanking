package com.mob.casestudy.digitalbanking.controller;

import com.digitalbanking.openapi.model.CreateCustomerSecurityQuestionsRequest;
import com.mob.casestudy.digitalbanking.service.CustomerSecurityImagesService;
import com.mob.casestudy.digitalbanking.service.CustomerService;
import com.mob.casestudy.digitalbanking.service.SecurityImagesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OpenApiControllerTest {

    @InjectMocks
    OpenApiController openApiController;

    @Mock
    SecurityImagesService securityImagesService;

    @Mock
    CustomerService customerService;

    @Mock
    CustomerSecurityImagesService customerSecurityImagesService;

    @Test
    void getSecurityImages() {
        openApiController.getSecurityImages();
        Mockito.verify(securityImagesService).retrieveSecurityImages();
    }

    @Test
    void deleteCustomerByUserName() {
        String name = "Dipak";
        openApiController.deleteCustomerByUserName(name);
        Mockito.verify(customerService).deleteCustomer(name);
    }

    @Test
    void getSecurityImageByUserName() {
        String name = "Dipak";
        openApiController.getSecurityImageByUserName(name);
        Mockito.verify(customerSecurityImagesService).retrieveSecurityImageByUserName(name);
    }

    @Test
    void createSecurityQuestionsByUserName() {
        String name = "Dipak";
        CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest = new CreateCustomerSecurityQuestionsRequest();
        openApiController.createSecurityQuestionsByUserName(name, createCustomerSecurityQuestionsRequest);
        Mockito.verify(customerService).createSecurityQuestions(name, createCustomerSecurityQuestionsRequest);
    }
}