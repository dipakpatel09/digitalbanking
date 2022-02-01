package com.mob.casestudy.digitalbanking.controller;

import com.mob.casestudy.digitalbanking.dto.SecurityQuestionsDtoList;
import com.mob.casestudy.digitalbanking.service.CustomerService;
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
        SecurityQuestionsDtoList securityQuestionsDtoList = new SecurityQuestionsDtoList();
        ResponseEntity<Object> expected = ResponseEntity.status(HttpStatus.CREATED).build();
        ResponseEntity<Object> actual = digitalbankingController.createSecurityQuestionsByUserName(name, securityQuestionsDtoList);
        Mockito.verify(customerService).createSecurityQuestions(name, securityQuestionsDtoList);
        Assertions.assertEquals(expected, actual);
    }
}