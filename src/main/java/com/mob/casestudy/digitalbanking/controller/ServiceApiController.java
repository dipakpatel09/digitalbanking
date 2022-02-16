package com.mob.casestudy.digitalbanking.controller;

import com.digitalbanking.openapi.api.ServiceApiApi;
import com.digitalbanking.openapi.model.GetSecurityImagesResponse;
import com.digitalbanking.openapi.model.ValidateOtpRequest;
import com.mob.casestudy.digitalbanking.service.CustomerOTPService;
import com.mob.casestudy.digitalbanking.service.SecurityImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer-service")
public class ServiceApiController implements ServiceApiApi {

    @Autowired
    SecurityImagesService securityImagesService;

    @Autowired
    CustomerOTPService customerOTPService;

    @Override
    public ResponseEntity<GetSecurityImagesResponse> getSecurityImages() {
        return securityImagesService.retrieveSecurityImages();
    }

    @Override
    public ResponseEntity<Void> validateOtp(ValidateOtpRequest validateOtpRequest) {
        return customerOTPService.validateOTPForUser(validateOtpRequest);
    }
}
