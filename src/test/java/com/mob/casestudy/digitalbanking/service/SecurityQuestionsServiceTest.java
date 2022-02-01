package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.dto.SecurityQuestionsDto;
import com.mob.casestudy.digitalbanking.dto.SecurityQuestionsDtoList;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityQuestions;
import com.mob.casestudy.digitalbanking.entity.SecurityQuestions;
import com.mob.casestudy.digitalbanking.exception.CustomerQuestionNotFoundException;
import com.mob.casestudy.digitalbanking.exception.CustomerQuestionSizeNotValidException;
import com.mob.casestudy.digitalbanking.exception.ValidationFailedException;
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
        Assertions.assertThrows(CustomerQuestionNotFoundException.class, () -> securityQuestionsService.validateQuestionId(id));
    }

    @Test
    void validateQuestionId_withValidQuestionId_shouldReturnSecurityQuestion() {
        UUID id = UUID.randomUUID();
        SecurityQuestions securityQuestions=new SecurityQuestions("abc");
        Mockito.when(securityQuestionsRepo.findById(id)).thenReturn(Optional.of(securityQuestions));
        SecurityQuestions securityQuestions1 = securityQuestionsService.validateQuestionId(id);
        Assertions.assertEquals(securityQuestions,securityQuestions1);
    }

    @Test
    void validateSecurityQuestion_withEmptySecurityQuestionList_shouldThrowException() {
        List<SecurityQuestionsDto> securityQuestions=new ArrayList<>();
        SecurityQuestionsDtoList securityQuestionsDtoList=new SecurityQuestionsDtoList();
        securityQuestionsDtoList.setSecurityQuestions(securityQuestions);
        Assertions.assertThrows(ValidationFailedException.class, () -> securityQuestionsService.validateSecurityQuestion(securityQuestionsDtoList));
    }

    @Test
    void validateSecurityQuestion_withValidSecurityQuestionList_shouldReturnSecurityQuestion() {
        List<SecurityQuestionsDto> securityQuestions=new ArrayList<>();
        securityQuestions.add(new SecurityQuestionsDto());
        SecurityQuestionsDtoList securityQuestionsDtoList=new SecurityQuestionsDtoList();
        securityQuestionsDtoList.setSecurityQuestions(securityQuestions);
        securityQuestionsService.validateSecurityQuestion(securityQuestionsDtoList);
    }

    @Test
    void validateSecurityQuestionSize_withInvalidSize_shouldThrowException() {
        List<SecurityQuestionsDto> securityQuestions=new ArrayList<>();
        securityQuestions.add(new SecurityQuestionsDto());
        SecurityQuestionsDtoList securityQuestionsDtoList=new SecurityQuestionsDtoList();
        securityQuestionsDtoList.setSecurityQuestions(securityQuestions);
        Assertions.assertThrows(CustomerQuestionSizeNotValidException.class, () -> securityQuestionsService.validateSecurityQuestionSize(securityQuestionsDtoList));
    }

    @Test
    void validateSecurityQuestionSize_withValidSize_shouldReturnSecurityQuestion() {
        List<SecurityQuestionsDto> securityQuestions=new ArrayList<>();
        securityQuestions.add(new SecurityQuestionsDto());
        securityQuestions.add(new SecurityQuestionsDto());
        securityQuestions.add(new SecurityQuestionsDto());
        SecurityQuestionsDtoList securityQuestionsDtoList=new SecurityQuestionsDtoList();
        securityQuestionsDtoList.setSecurityQuestions(securityQuestions);
        securityQuestionsService.validateSecurityQuestionSize(securityQuestionsDtoList);
    }
}