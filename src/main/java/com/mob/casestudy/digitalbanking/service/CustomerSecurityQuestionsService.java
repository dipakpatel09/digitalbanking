package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.repository.CustomerSecurityQuestionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerSecurityQuestionsService {

    @Autowired
    CustomerSecurityQuestionsRepo customerSecurityQuestionsRepo;

    public void deleteCustomerQuestion(Customer customer) {
        if (!customer.getCustomerSecurityQuestions().isEmpty()) {
            customerSecurityQuestionsRepo.deleteAll();
        }
    }
}
