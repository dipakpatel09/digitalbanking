package com.mob.casestudy.digitalbanking.controller;

import com.digitalbanking.openapi.api.ClientApiApi;
import com.digitalbanking.openapi.api.ServiceApiApi;
import com.digitalbanking.openapi.model.CreateCustomerSecurityQuestionsRequest;
import com.digitalbanking.openapi.model.GetCustomerSecurityImageResponse;
import com.digitalbanking.openapi.model.GetSecurityImagesResponse;
import com.mob.casestudy.digitalbanking.service.CustomerSecurityImagesService;
import com.mob.casestudy.digitalbanking.service.CustomerService;
import com.mob.casestudy.digitalbanking.service.SecurityImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@RestController
@RequestMapping("/customer-service")
public class OpenApiController implements ServiceApiApi, ClientApiApi {

    @Autowired
    SecurityImagesService securityImagesService;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerSecurityImagesService customerSecurityImagesService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return ServiceApiApi.super.getRequest();
    }

    @Override
    public ResponseEntity<GetSecurityImagesResponse> getSecurityImages() {
        return securityImagesService.retrieveSecurityImages();
    }

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
        return customerService.createSecurityQuestions(username, createCustomerSecurityQuestionsRequest);
    }
}
