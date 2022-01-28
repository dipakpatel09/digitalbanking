package com.mob.casestudy.digitalbanking.controller;

import com.mob.casestudy.digitalbanking.dto.CustomerSecurityQuestionsDto;
import com.mob.casestudy.digitalbanking.dto.CustomerSecurityQuestionsDtoList;
import com.mob.casestudy.digitalbanking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customer-service")
public class DigitalbankingController {

    @Autowired
    CustomerService customerService;

    //TODO: Remove unwanted injections

    @DeleteMapping("/client-api/v1/customers/{userName}")
    public ResponseEntity<Object> deleteCustomerByUserName(@PathVariable String userName) {
        customerService.deleteCustomer(userName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/client-api/v1/customers/{userName}/securityQuestions")
    public ResponseEntity<Object> createSecurityQuestionsByUserName(@PathVariable String userName, @Valid @RequestBody CustomerSecurityQuestionsDtoList customerSecurityQuestionsDtoList) {
        customerService.createSecurityQuestions(userName, customerSecurityQuestionsDtoList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
