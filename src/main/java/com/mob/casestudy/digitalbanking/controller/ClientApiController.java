package com.mob.casestudy.digitalbanking.controller;

import com.digitalbanking.openapi.api.ClientApiApi;
import com.digitalbanking.openapi.model.*;
import com.mob.casestudy.digitalbanking.service.CustomerSecurityImagesService;
import com.mob.casestudy.digitalbanking.service.CustomerSecurityQuestionsService;
import com.mob.casestudy.digitalbanking.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer-service")
public class ClientApiController implements ClientApiApi {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerSecurityImagesService customerSecurityImagesService;

    @Autowired
    CustomerSecurityQuestionsService customerSecurityQuestionsService;

    @Override
    public ResponseEntity<Void> deleteCustomerByUserName(String username) {
        return customerService.deleteCustomer(username);
    }

    @Override
    public ResponseEntity<GetCustomerSecurityImageResponse> getSecurityImageByUserName(String username) {
        return customerSecurityImagesService.retrieveSecurityImageByUserName(username);
    }

    @Override
    public ResponseEntity<Void> createSecurityQuestionsByUserName(String username, CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest) {
        return customerSecurityQuestionsService.createSecurityQuestions(username, createCustomerSecurityQuestionsRequest);
    }

    @Override
    public ResponseEntity<CreateCustomerResponse> postCustomers(CreateCustomerRequest createCustomerRequest) {
        return customerService.createCustomer(createCustomerRequest);
    }

    @Override
    public ResponseEntity<GetCustomerResponse> getCustomers(String id, String userName) {
        return customerService.retrieveCustomer(id, userName);
    }
}
