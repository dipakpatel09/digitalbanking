package com.mob.casestudy.digitalbanking.dto;

import javax.validation.Valid;
import java.util.List;

public class CreateCustomerSecurityQuestionsRequest {

    @Valid
    private List<SecurityQuestionsDto> securityQuestions;

    public CreateCustomerSecurityQuestionsRequest() {
    }

    public CreateCustomerSecurityQuestionsRequest(List<SecurityQuestionsDto> securityQuestions) {
        this.securityQuestions = securityQuestions;
    }

    public List<SecurityQuestionsDto> getSecurityQuestions() {
        return securityQuestions;
    }

    public void setSecurityQuestions(List<SecurityQuestionsDto> securityQuestions) {
        this.securityQuestions = securityQuestions;
    }
}
