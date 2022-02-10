package com.mob.casestudy.digitalbanking.service;

import com.digitalbanking.openapi.model.CreateCustomerSecurityQuestionsRequest;
import com.digitalbanking.openapi.model.SecurityQuestion;
import com.mob.casestudy.digitalbanking.entity.SecurityQuestions;
import com.mob.casestudy.digitalbanking.exception.CustomBadRequestException;
import com.mob.casestudy.digitalbanking.exception.CustomNotFoundException;
import com.mob.casestudy.digitalbanking.repository.SecurityQuestionsRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class SecurityQuestionsServiceTest {

    @InjectMocks
    SecurityQuestionsService securityQuestionsService;

    @Mock
    SecurityQuestionsRepo securityQuestionsRepo;

    @Test
    void validateQuestionId_withInvalidQuestionId_shouldThrowException() {
        UUID id = UUID.randomUUID();
        Mockito.when(securityQuestionsRepo.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(CustomNotFoundException.class, () -> securityQuestionsService.validateQuestionId(id));
    }

    @Test
    void validateQuestionId_withValidQuestionId_shouldReturnSecurityQuestion() {
        UUID id = UUID.randomUUID();
        SecurityQuestions securityQuestions = new SecurityQuestions("What is your favourite Place?");
        Mockito.when(securityQuestionsRepo.findById(id)).thenReturn(Optional.of(securityQuestions));
        SecurityQuestions securityQuestions1 = securityQuestionsService.validateQuestionId(id);
        Assertions.assertEquals(securityQuestions, securityQuestions1);
    }

    @Test
    void validateSecurityQuestion_withEmptySecurityQuestionList_shouldThrowException() {
        List<SecurityQuestion> securityQuestions = new ArrayList<>();
        CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest = new CreateCustomerSecurityQuestionsRequest();
        createCustomerSecurityQuestionsRequest.setSecurityQuestions(securityQuestions);
        Assertions.assertThrows(CustomBadRequestException.class, () -> securityQuestionsService.validateSecurityQuestion(createCustomerSecurityQuestionsRequest));
    }

    @Test
    void validateSecurityQuestion_withValidSecurityQuestionList_shouldReturnSecurityQuestion() {
        List<SecurityQuestion> securityQuestions = new ArrayList<>();
        securityQuestions.add(new SecurityQuestion());
        CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest = new CreateCustomerSecurityQuestionsRequest();
        createCustomerSecurityQuestionsRequest.setSecurityQuestions(securityQuestions);
        securityQuestionsService.validateSecurityQuestion(createCustomerSecurityQuestionsRequest);
    }

    @Test
    void validateSecurityQuestionSize_withInvalidSize_shouldThrowException() {
        List<SecurityQuestion> securityQuestions = new ArrayList<>();
        securityQuestions.add(new SecurityQuestion());
        CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest = new CreateCustomerSecurityQuestionsRequest();
        createCustomerSecurityQuestionsRequest.setSecurityQuestions(securityQuestions);
        Assertions.assertThrows(CustomBadRequestException.class, () -> securityQuestionsService.validateSecurityQuestionSize(createCustomerSecurityQuestionsRequest));
    }

    @Test
    void validateSecurityQuestionSize_withValidSize_shouldReturnSecurityQuestion() {
        List<SecurityQuestion> securityQuestions = new ArrayList<>();
        securityQuestions.add(new SecurityQuestion());
        securityQuestions.add(new SecurityQuestion());
        securityQuestions.add(new SecurityQuestion());
        CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest = new CreateCustomerSecurityQuestionsRequest();
        createCustomerSecurityQuestionsRequest.setSecurityQuestions(securityQuestions);
        securityQuestionsService.validateSecurityQuestionSize(createCustomerSecurityQuestionsRequest);
    }
}