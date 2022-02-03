package com.mob.casestudy.digitalbanking.controller;

import com.mob.casestudy.digitalbanking.dto.CreateCustomerSecurityQuestionsRequest;
import com.mob.casestudy.digitalbanking.dto.GetSecurityImagesResponse;
import com.mob.casestudy.digitalbanking.dto.SecurityImagesDto;
import com.mob.casestudy.digitalbanking.entity.SecurityImages;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class DigitalbankingControllerTest {

    @InjectMocks
    DigitalbankingController digitalbankingController;

    @Mock
    CustomerService customerService;

    @Mock
    SecurityImagesService securityImagesService;

    @Test
    void deleteCustomerByUserName() {
        ResponseEntity<Object> expected = ResponseEntity.noContent().build();
        String name = "Neel";
        ResponseEntity<Object> actual = digitalbankingController.deleteCustomerByUserName(name);
        Mockito.verify(customerService).deleteCustomer(name);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createSecurityQuestionsByUserName() {
        String name = "Uzair";
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
        Mockito.verify(securityImagesService).getSecurityImages();
        Assertions.assertEquals(expected, actual);
    }
}