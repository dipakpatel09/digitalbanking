package com.mob.casestudy.digitalbanking.service;

import static com.mob.casestudy.digitalbanking.errorlist.CustomError.*;

import com.mob.casestudy.digitalbanking.dto.CreateCustomerSecurityQuestionsRequest;
import com.mob.casestudy.digitalbanking.exception.*;
import com.mob.casestudy.digitalbanking.repository.CustomerRepo;
import com.mob.casestudy.digitalbanking.repository.CustomerSecurityQuestionsRepo;
import com.mob.casestudy.digitalbanking.entity.*;
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
        Customer customer = findCustomerByUserName(userName, CUS_DELETE_NOT_FOUND, "Invalid User.. " + userName);
        customerRepo.delete(customer);
    }

    public Customer findCustomerByUserName(String userName, String errorCode, String errorDescription) {
        return customerRepo.findByUserName(userName).orElseThrow(() -> new CustomNotFoundException(errorCode, errorDescription));
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
            throw new CustomNotFoundException(CUS_SEC_QUES_CUS_NOT_FOUND, "The requested user not found.. " + userName);
        }
        return byUserName.get();
    }

    private void addCustomerQuestion(Customer customer, CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest) {
        for (int i = 0; i < 3; i++) {
            String ansLength = createCustomerSecurityQuestionsRequest.getSecurityQuestions().get(i).getSecurityQuestionAnswer().trim();
            if (ansLength.length() < 3) {
                throw new CustomBadRequestException(CUS_SEC_QUES_VALIDATE_ERROR, "Security question answer should be minimum 3 Characters");
            }
            CustomerSecurityQuestions customerSecurityQuestions = new CustomerSecurityQuestions();
            customer.addCustomerSecurityQuestions(customerSecurityQuestions);
            customerSecurityQuestions.setCustomer(customer);
            SecurityQuestions securityQuestions = securityQuestionsService.validateQuestionId(UUID.fromString(createCustomerSecurityQuestionsRequest.getSecurityQuestions().get(i).getSecurityQuestionId()));
            customerSecurityQuestions.setSecurityQuestions(securityQuestions);
            customerSecurityQuestions.setCreatedOn(LocalDateTime.now());
            customerSecurityQuestions.setSecurityQuestionAnswer(ansLength);
            customerSecurityQuestionsRepo.save(customerSecurityQuestions);
        }
    }
}
