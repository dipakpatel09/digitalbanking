package com.mob.casestudy.digitalbanking.dto;

import javax.validation.Valid;
import java.util.List;

public class SecurityQuestionsDtoList {

    @Valid
    private List<SecurityQuestionsDto> securityQuestions;

    public SecurityQuestionsDtoList() {
    }

    public SecurityQuestionsDtoList(List<SecurityQuestionsDto> securityQuestions) {
        this.securityQuestions = securityQuestions;
    }

    public List<SecurityQuestionsDto> getSecurityQuestions() {
        return securityQuestions;
    }

    public void setSecurityQuestions(List<SecurityQuestionsDto> securityQuestions) {
        this.securityQuestions = securityQuestions;
    }
}
