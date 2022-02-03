package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.dto.CreateCustomerSecurityQuestionsRequest;
import com.mob.casestudy.digitalbanking.repository.CustomerRepo;
import com.mob.casestudy.digitalbanking.repository.CustomerSecurityQuestionsRepo;
import com.mob.casestudy.digitalbanking.entity.*;
import com.mob.casestudy.digitalbanking.exception.CustomerNotFoundException;
import com.mob.casestudy.digitalbanking.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    CustomerSecurityQuestionsRepo customerSecurityQuestionsRepo;

    @Autowired
    SecurityQuestionsService securityQuestionsService;

    @Autowired
    CustomerSecurityQuestionsService customerSecurityQuestionsService;

    @Transactional
    public void deleteCustomer(String userName) {
        Customer customer = findCustomerByUserName(userName);
        customerRepo.delete(customer);
    }

    private Customer findCustomerByUserName(String userName) {
        return customerRepo.findByUserName(userName).orElseThrow(() -> new UserNotFoundException("Invalid User.." + userName));
    }

    @Transactional
    public void createSecurityQuestions(String userName, CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest) {
        Customer customer = validateCustomer(userName);
        securityQuestionsService.validateSecurityQuestion(createCustomerSecurityQuestionsRequest);
        securityQuestionsService.validateSecurityQuestionSize(createCustomerSecurityQuestionsRequest);
        customerSecurityQuestionsService.deleteCustomerQuestion(customer);
        addCustomerQuestion(customer, createCustomerSecurityQuestionsRequest);
    }

    private Customer validateCustomer(String userName) {
        Optional<Customer> byUserName = customerRepo.findByUserName(userName);
        if (byUserName.isEmpty()) {
            throw new CustomerNotFoundException("The requested user not found.." + userName);
        }
        return byUserName.get();
    }

    private void addCustomerQuestion(Customer customer, CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest) {
        for (int i = 0; i < 3; i++) {
            CustomerSecurityQuestions customerSecurityQuestions = new CustomerSecurityQuestions();
            customer.addCustomerSecurityQuestions(customerSecurityQuestions);
            customerSecurityQuestions.setCustomer(customer);
            SecurityQuestions securityQuestions = securityQuestionsService.validateQuestionId(UUID.fromString(createCustomerSecurityQuestionsRequest.getSecurityQuestions().get(i).getSecurityQuestionId()));
            customerSecurityQuestions.setSecurityQuestions(securityQuestions);
            customerSecurityQuestions.setCreatedOn(LocalDateTime.now());
            customerSecurityQuestions.setSecurityQuestionAnswer(createCustomerSecurityQuestionsRequest.getSecurityQuestions().get(i).getSecurityQuestionAnswer().trim());
            customerSecurityQuestionsRepo.save(customerSecurityQuestions);
        }
    }
}
