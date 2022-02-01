package com.mob.casestudy.digitalbanking.dto;

import com.mob.casestudy.digitalbanking.embedded.CustomerSecQuestion;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityQuestions;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class SecurityQuestionsDto {

    private String securityQuestionId;

    private String securityQuestionText;

    @NotBlank(message = "Security question answer should not contain only blank spaces")
    @NotNull(message = "Security question answer should not be Null")
    @NotEmpty(message = "Security question answer should not be Empty")
    @Size(min = 3, message = "Security question answer should be minimum 3 Characters")
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

    public CustomerSecurityQuestions toEntity() {

        return new CustomerSecurityQuestions(new CustomerSecQuestion(),getSecurityQuestionAnswer());
    }
}
