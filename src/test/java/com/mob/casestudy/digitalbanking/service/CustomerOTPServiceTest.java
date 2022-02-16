package com.mob.casestudy.digitalbanking.service;

import static com.mob.casestudy.digitalbanking.customerror.ErrorList.*;

import com.digitalbanking.openapi.model.ValidateOtpRequest;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerOTP;
import com.mob.casestudy.digitalbanking.validation.ValidationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class CustomerOTPServiceTest {

    @InjectMocks
    CustomerOTPService customerOTPService;

    @Mock
    CustomerService customerService;

    @Mock
    ValidationService validationService;

    @Test
    void validateOTPForUser_withValidOTP_shouldValidateTheOTP() {
        String name = "Dipak";
        ValidateOtpRequest validateOtpRequest = new ValidateOtpRequest();
        validateOtpRequest.setOtp("123456");
        validateOtpRequest.setUserName(name);
        Customer customer = new Customer();
        CustomerOTP customerOTP = new CustomerOTP();
        customerOTP.setOtp("123456");
        customerOTP.setCreatedOn(LocalDateTime.now());
        customerOTP.setExpiryOn(LocalDateTime.now().plusMinutes(1));
        customerOTP.setOtpRetries(1);
        customer.setCustomerOTP(customerOTP);
        Mockito.when(customerService.findCustomerByUserName(name, CUS_NOT_FOUND, name + " user not found")).thenReturn(customer);
        ResponseEntity<Object> expected = ResponseEntity.ok().build();
        ResponseEntity<Void> actual = customerOTPService.validateOTPForUser(validateOtpRequest);
        Mockito.verify(validationService).validateOTP(validateOtpRequest);
        Mockito.verify(validationService).validateOTPExpiryTime(customerOTP);
        Mockito.verify(validationService).validateOTPAttempt(customerOTP, validateOtpRequest);
        Assertions.assertEquals(expected, actual);
    }
}