package com.mob.casestudy.digitalbanking.controller;

import com.mob.casestudy.digitalbanking.dto.CreateCustomerSecurityQuestionsRequest;
import com.mob.casestudy.digitalbanking.dto.CustomerSecurityImagesDto;
import com.mob.casestudy.digitalbanking.dto.GetSecurityImagesResponse;
import com.mob.casestudy.digitalbanking.service.CustomerSecurityImagesService;
import com.mob.casestudy.digitalbanking.service.CustomerService;
import com.mob.casestudy.digitalbanking.service.SecurityImagesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class DigitalbankingControllerTest {

    @InjectMocks
    DigitalbankingController digitalbankingController;

    @Mock
    CustomerService customerService;

    @Mock
    SecurityImagesService securityImagesService;

    @Mock
    CustomerSecurityImagesService customerSecurityImagesService;

    @Test
    void deleteCustomerByUserName() {
        ResponseEntity<Object> expected = ResponseEntity.noContent().build();
        String name = "Dipak";
        ResponseEntity<Object> actual = digitalbankingController.deleteCustomerByUserName(name);
        Mockito.verify(customerService).deleteCustomer(name);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createSecurityQuestionsByUserName() {
        String name = "Dipak";
        CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest = new CreateCustomerSecurityQuestionsRequest();
        ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CREATED).build();
        ResponseEntity<Object> actual = digitalbankingController.createSecurityQuestionsByUserName(name, createCustomerSecurityQuestionsRequest);
        Mockito.verify(customerService).createSecurityQuestions(name, createCustomerSecurityQuestionsRequest);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getSecurityImages() {
        GetSecurityImagesResponse securityImagesResponse = new GetSecurityImagesResponse();
        Mockito.when(securityImagesService.getSecurityImages()).thenReturn(securityImagesResponse);
        ResponseEntity<Object> expected = ResponseEntity.ok().body(securityImagesResponse);
        ResponseEntity<Object> actual = digitalbankingController.getSecurityImages();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getSecurityImageByUserName() {
        String name = "Dipak";
        CustomerSecurityImagesDto customerSecurityImagesDto = new CustomerSecurityImagesDto();
        Mockito.when(customerSecurityImagesService.getSecurityImageByUserName(name)).thenReturn(customerSecurityImagesDto);
        ResponseEntity<Object> expected = ResponseEntity.ok().body(customerSecurityImagesDto);
        ResponseEntity<Object> actual = digitalbankingController.getSecurityImageByUserName(name);
        Assertions.assertEquals(expected, actual);
    }
}