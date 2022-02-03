package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.dto.CreateCustomerSecurityQuestionsRequest;
import com.mob.casestudy.digitalbanking.entity.SecurityQuestions;
import com.mob.casestudy.digitalbanking.exception.CustomerQuestionNotFoundException;
import com.mob.casestudy.digitalbanking.exception.CustomerQuestionSizeNotValidException;
import com.mob.casestudy.digitalbanking.exception.ValidationFailedException;
import com.mob.casestudy.digitalbanking.repository.SecurityQuestionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SecurityQuestionsService {

    @Autowired
    SecurityQuestionsRepo securityQuestionsRepo;

    public SecurityQuestions validateQuestionId(UUID id) {
        Optional<SecurityQuestions> byId = securityQuestionsRepo.findById(id);
        if (byId.isEmpty()) {
            throw new CustomerQuestionNotFoundException("Customer question not found..");
        }
        return byId.get();
    }

    public void validateSecurityQuestion(CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest) {
        if (createCustomerSecurityQuestionsRequest.getSecurityQuestions().isEmpty()) {
            throw new ValidationFailedException("Customer security question can't be Empty");
        }
    }

    public void validateSecurityQuestionSize(CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest) {
        if (createCustomerSecurityQuestionsRequest.getSecurityQuestions().size() != 3) {
            throw new CustomerQuestionSizeNotValidException("3 questions are allowed.");
        }
    }
}
