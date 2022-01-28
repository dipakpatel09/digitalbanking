package com.mob.casestudy.digitalbanking.dto;

import java.util.List;

public class CustomerSecurityQuestionsDtoList {

    private List<CustomerSecurityQuestionsDto> securityQuestions;

    public CustomerSecurityQuestionsDtoList() {
    }

    public CustomerSecurityQuestionsDtoList(List<CustomerSecurityQuestionsDto> securityQuestions) {
        this.securityQuestions = securityQuestions;
    }

    public List<CustomerSecurityQuestionsDto> getCustomerSecurityQuestionsDtos() {
        return securityQuestions;
    }

    public void setCustomerSecurityQuestionsDtos(List<CustomerSecurityQuestionsDto> securityQuestions) {
        this.securityQuestions = securityQuestions;
    }
}
