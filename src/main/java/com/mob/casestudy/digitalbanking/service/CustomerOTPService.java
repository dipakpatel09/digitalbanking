package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.repository.CustomerOTPRepo;
import com.mob.casestudy.digitalbanking.entity.CustomerOTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerOTPService {

    @Autowired
    CustomerOTPRepo customerOTPRepo;

    public void deleteCustomerOTP(CustomerOTP customerOTP) {
        if (customerOTP != null) {
            customerOTPRepo.delete(customerOTP);
        }
    }
}
