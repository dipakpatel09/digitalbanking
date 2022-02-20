package com.mob.casestudy.digitalbanking.service;

import com.digitalbanking.openapi.model.CreateCustomerSecurityQuestionsRequest;
import com.digitalbanking.openapi.model.SecurityQuestion;
import com.mob.casestudy.digitalbanking.entity.Customer;
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
        for (int i = 0; i < 3; i++) {
            SecurityQuestions securityQuestions = validationService.validateQuestionId(UUID.fromString(createCustomerSecurityQuestionsRequest.getSecurityQuestions().get(i).getSecurityQuestionId()), securityQuestionsList);
            SecurityQuestion securityQuestion = createCustomerSecurityQuestionsRequest.getSecurityQuestions().get(i);
            customerSecurityQuestionsRepo.save(customerSecurityQuestionsMapper.fromDto(securityQuestion, customer, securityQuestions, LocalDateTime.now()));
        }
    }

    public void deleteCustomerQuestion(Customer customer) {
        if (!customer.getCustomerSecurityQuestions().isEmpty()) {
            customerSecurityQuestionsRepo.deleteAll();
        }
    }
}
