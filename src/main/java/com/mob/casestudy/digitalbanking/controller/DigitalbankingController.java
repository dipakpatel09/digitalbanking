package com.mob.casestudy.digitalbanking.controller;

import com.mob.casestudy.digitalbanking.dto.CreateCustomerSecurityQuestionsRequest;
import com.mob.casestudy.digitalbanking.dto.CustomerSecurityImagesDto;
import com.mob.casestudy.digitalbanking.service.CustomerSecurityImagesService;
import com.mob.casestudy.digitalbanking.service.CustomerService;
import com.mob.casestudy.digitalbanking.service.SecurityImagesService;
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

    @Autowired
    SecurityImagesService securityImagesService;

    @Autowired
    CustomerSecurityImagesService customerSecurityImagesService;

    @DeleteMapping("/client-api/v1/customers/{userName}")
    public ResponseEntity<Object> deleteCustomerByUserName(@PathVariable String userName) {
        customerService.deleteCustomer(userName);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/client-api/v1/customers/{userName}/securityQuestions")
    public ResponseEntity<Object> createSecurityQuestionsByUserName(@PathVariable String userName, @RequestBody @Valid CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest) {
        customerService.createSecurityQuestions(userName, createCustomerSecurityQuestionsRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/service-api/v2/securityImages")
    public ResponseEntity<Object> getSecurityImages() {
        return ResponseEntity.ok().body(securityImagesService.getSecurityImages());
    }

    @GetMapping("/client-api/v1/customers/{userName}/securityImages")
    public ResponseEntity<Object> getSecurityImageByUserName(@PathVariable String userName) {
        CustomerSecurityImagesDto customerSecurityImagesDto = customerSecurityImagesService.getSecurityImageByUserName(userName);
        return ResponseEntity.ok().body(customerSecurityImagesDto);
    }
}
