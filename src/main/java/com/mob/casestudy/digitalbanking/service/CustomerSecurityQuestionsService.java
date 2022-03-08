package com.mob.casestudy.digitalbanking.service;

import com.digitalbanking.openapi.model.CreateCustomerSecurityQuestionsRequest;
import com.digitalbanking.openapi.model.SecurityQuestion;
import com.mob.casestudy.digitalbanking.embedded.CustomerSecQuestion;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityQuestions;
import com.mob.casestudy.digitalbanking.entity.SecurityQuestions;
import com.mob.casestudy.digitalbanking.mapper.CustomerSecurityQuestionsMapperImpl;
import com.mob.casestudy.digitalbanking.repository.CustomerSecurityQuestionsRepo;
import com.mob.casestudy.digitalbanking.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mob.casestudy.digitalbanking.customerror.ErrorList.CUS_SEC_QUES_CUS_NOT_FOUND;

@Service
public class CustomerSecurityQuestionsService {

    @Autowired
    CustomerSecurityQuestionsRepo customerSecurityQuestionsRepo;

    @Autowired
    CustomerService customerService;

    @Autowired
    SecurityQuestionsService securityQuestionsService;

    @Autowired
    ValidationService validationService;

    @Autowired
    CustomerSecurityQuestionsMapperImpl customerSecurityQuestionsMapper;

    @Transactional
    public ResponseEntity<Void> createSecurityQuestions(String userName, CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest) {
        Customer customer = customerService.findCustomerByUserName(userName, CUS_SEC_QUES_CUS_NOT_FOUND, "The requested user not found.. " + userName);
        validationService.validateSecurityQuestion(createCustomerSecurityQuestionsRequest);
        validationService.validateSecurityQuestionSize(createCustomerSecurityQuestionsRequest);
        validationService.validateSecurityQuestionAnswer(createCustomerSecurityQuestionsRequest);
        List<SecurityQuestions> securityQuestionsList = securityQuestionsService.findAllSecurityQuestion();
        deleteCustomerQuestion(customer);
        addCustomerQuestion(customer, createCustomerSecurityQuestionsRequest, securityQuestionsList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private void addCustomerQuestion(Customer customer, CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest, List<SecurityQuestions> securityQuestionsList) {
        List<CustomerSecurityQuestions> customerSecurityQuestions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            SecurityQuestion securityQuestion = createCustomerSecurityQuestionsRequest.getSecurityQuestions().get(i);
            UUID id = UUID.fromString(securityQuestion.getSecurityQuestionId());
            SecurityQuestions securityQuestions = validationService.validateQuestionId(id, securityQuestionsList);
            customerSecurityQuestions.add(customerSecurityQuestionsMapper.fromDto(securityQuestion, customer, securityQuestions, LocalDateTime.now(),new CustomerSecQuestion()));
        }
        customerSecurityQuestionsRepo.saveAll(customerSecurityQuestions);
    }

    public void deleteCustomerQuestion(Customer customer) {
        if (!customer.getCustomerSecurityQuestions().isEmpty()) {
            customerSecurityQuestionsRepo.deleteAll();
        }
    }
}
