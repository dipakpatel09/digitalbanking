package com.mob.casestudy.digitalbanking.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SecurityQuestionsDto {

    private String securityQuestionId;

    private String securityQuestionText;

    @NotBlank(message = "Security question answer should not contain only blank spaces")
    @NotNull(message = "Security question answer should not be Null")
    @NotEmpty(message = "Security question answer should not be Empty")
    private String securityQuestionAnswer;

    public SecurityQuestionsDto() {
    }

    public SecurityQuestionsDto(String securityQuestionId, String securityQuestionText, String securityQuestionAnswer) {
        this.securityQuestionId = securityQuestionId;
        this.securityQuestionText = securityQuestionText;
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public String getSecurityQuestionId() {
        return securityQuestionId;
    }

    public void setSecurityQuestionId(String securityQuestionId) {
        this.securityQuestionId = securityQuestionId;
    }

    public String getSecurityQuestionText() {
        return securityQuestionText;
    }

    public void setSecurityQuestionText(String securityQuestionText) {
        this.securityQuestionText = securityQuestionText;
    }

    public String getSecurityQuestionAnswer() {
        return securityQuestionAnswer;
    }

    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = securityQuestionAnswer;
    }
}
