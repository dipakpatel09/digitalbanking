package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.repository.CustomerOTPRepo;
import com.mob.casestudy.digitalbanking.entity.CustomerOTP;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerOTPServiceTest {

    @InjectMocks
    CustomerOTPService customerOTPService;

    @Mock
    CustomerOTPRepo customerOTPRepo;

    @Test
    void deleteCustomerOTP_withNullOTP_shouldNotReturnAnything() {

        customerOTPService.deleteCustomerOTP(null);
        Mockito.verify(customerOTPRepo,Mockito.times(0)).delete(Mockito.any());
    }

    @Test
    void deleteCustomerOTP_withValidOTP_shouldDeleteOTP() {

        CustomerOTP customerOTP=new CustomerOTP();
        customerOTPService.deleteCustomerOTP(customerOTP);
        Mockito.verify(customerOTPRepo).delete(customerOTP);

    }
}