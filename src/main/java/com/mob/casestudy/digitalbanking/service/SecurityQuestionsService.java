package com.mob.casestudy.digitalbanking.service;

import static com.mob.casestudy.digitalbanking.errorlist.CustomError.*;


import com.digitalbanking.openapi.model.CreateCustomerSecurityQuestionsRequest;
import com.mob.casestudy.digitalbanking.entity.SecurityQuestions;
import com.mob.casestudy.digitalbanking.exception.CustomBadRequestException;
import com.mob.casestudy.digitalbanking.exception.CustomNotFoundException;
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
            throw new CustomNotFoundException(CUS_SEC_QUES_NOT_FOUND, "Customer security question not found..");
        }
        return byId.get();
    }

    public void validateSecurityQuestion(CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest) {
        if (createCustomerSecurityQuestionsRequest.getSecurityQuestions().isEmpty()) {
            throw new CustomBadRequestException(CUS_SEC_QUES_CREATE_FIELD_ERROR, "Customer security question can't be Empty");
        }
    }

    public void validateSecurityQuestionSize(CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest) {
        if (createCustomerSecurityQuestionsRequest.getSecurityQuestions().size() != 3) {
            throw new CustomBadRequestException(CUS_SEC_QUES_CREATE_3_QUES_ERROR, "3 questions are allowed.");
        }
    }
}
