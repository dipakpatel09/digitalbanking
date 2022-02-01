package com.mob.casestudy.digitalbanking.controller;

import com.mob.casestudy.digitalbanking.dto.SecurityQuestionsDtoList;
import com.mob.casestudy.digitalbanking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customer-service")
public class DigitalbankingController {

    @Autowired
    CustomerService customerService;

    @DeleteMapping("/client-api/v1/customers/{userName}")
    public ResponseEntity<Object> deleteCustomerByUserName(@PathVariable String userName) {
        customerService.deleteCustomer(userName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/client-api/v1/customers/{userName}/securityQuestions")
    public ResponseEntity<Object> createSecurityQuestionsByUserName(@PathVariable String userName, @RequestBody @Valid SecurityQuestionsDtoList securityQuestionsDtoList) {
        customerService.createSecurityQuestions(userName, securityQuestionsDtoList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
