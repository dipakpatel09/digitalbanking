package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.repository.CustomerSecurityQuestionsRepo;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityQuestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerSecurityQuestionsService {

    @Autowired
    CustomerSecurityQuestionsRepo customerSecurityQuestionsRepo;

    public void deleteCustomerSecurityQuestions(List<CustomerSecurityQuestions> customerSecurityQuestions) {

        //TODO: Use deleteAll()
        //Remove Transactional annotation from class level

        customerSecurityQuestionsRepo.deleteAll(customerSecurityQuestions);
    }
}
