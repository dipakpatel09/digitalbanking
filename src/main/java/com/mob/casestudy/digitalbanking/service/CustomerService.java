package com.mob.casestudy.digitalbanking.service;

import static com.mob.casestudy.digitalbanking.customerror.ErrorList.*;

import com.mob.casestudy.digitalbanking.exception.*;
import com.mob.casestudy.digitalbanking.repository.CustomerRepo;
import com.mob.casestudy.digitalbanking.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    @Transactional
    public ResponseEntity<Void> deleteCustomer(String userName) {
        Customer customer = findCustomerByUserName(userName, CUS_DELETE_NOT_FOUND, "Invalid User.. " + userName);
        customerRepo.delete(customer);
        return ResponseEntity.noContent().build();
    }

    public Customer findCustomerByUserName(String userName, String errorCode, String errorDescription) {
        return customerRepo.findByUserName(userName).orElseThrow(() -> new CustomNotFoundException(errorCode, errorDescription));
    }
}
