package com.mob.casestudy.digitalbanking.service;

import static com.mob.casestudy.digitalbanking.customerror.ErrorList.*;

import com.digitalbanking.openapi.model.ValidateOtpRequest;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerOTP;
import com.mob.casestudy.digitalbanking.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerOTPService {

    @Autowired
    ValidationService validationService;

    @Autowired
    CustomerService customerService;

    public ResponseEntity<Void> validateOTPForUser(ValidateOtpRequest validateOtpRequest) {
        validationService.validateOTP(validateOtpRequest);
        String userName = validateOtpRequest.getUserName();
        Customer customer = customerService.findCustomerByUserName(userName, CUS_NOT_FOUND, userName + " user not found");
        CustomerOTP customerOTP = customer.getCustomerOTP();
        validationService.validateOTPExpiryTime(customerOTP);
        validationService.validateOTPAttempt(customerOTP, validateOtpRequest);
        return ResponseEntity.ok().build();
    }
}
