package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.entity.SecurityQuestions;
import com.mob.casestudy.digitalbanking.repository.SecurityQuestionsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityQuestionsService {

    @Autowired
    SecurityQuestionsRepo securityQuestionsRepo;

    public List<SecurityQuestions> findAllSecurityQuestion() {
        return securityQuestionsRepo.findAll();
    }
}
