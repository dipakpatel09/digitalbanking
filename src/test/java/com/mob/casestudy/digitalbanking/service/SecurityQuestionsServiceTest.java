package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.entity.SecurityQuestions;
import com.mob.casestudy.digitalbanking.repository.SecurityQuestionsRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class SecurityQuestionsServiceTest {

    @InjectMocks
    SecurityQuestionsService securityQuestionsService;

    @Mock
    SecurityQuestionsRepo securityQuestionsRepo;

    @Test
    void findAllSecurityQuestion() {
        List<SecurityQuestions> securityQuestionsList = new ArrayList<>();
        Mockito.when(securityQuestionsRepo.findAll()).thenReturn(securityQuestionsList);
        securityQuestionsService.findAllSecurityQuestion();
        Mockito.verify(securityQuestionsRepo).findAll();
    }
}