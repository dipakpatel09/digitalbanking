package com.mob.casestudy.digitalbanking.controller;

import com.digitalbanking.openapi.model.ValidateOtpRequest;
import com.mob.casestudy.digitalbanking.service.CustomerOTPService;
import org.junit.jupiter.api.Test;
import com.mob.casestudy.digitalbanking.service.SecurityImagesService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ServiceApiControllerTest {

    @InjectMocks
    ServiceApiController serviceApiController;

    @Mock
    SecurityImagesService securityImagesService;

    @Mock
    CustomerOTPService customerOTPService;

    @Test
    void getSecurityImages() {
        serviceApiController.getSecurityImages();
        Mockito.verify(securityImagesService).retrieveSecurityImages();
    }

    @Test
    void validateOtp() {
        ValidateOtpRequest validateOtpRequest=new ValidateOtpRequest();
        serviceApiController.validateOtp(validateOtpRequest);
        Mockito.verify(customerOTPService).validateOTPForUser(validateOtpRequest);
    }
}