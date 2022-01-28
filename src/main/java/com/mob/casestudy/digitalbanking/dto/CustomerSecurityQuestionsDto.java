package com.mob.casestudy.digitalbanking.dto;

import com.mob.casestudy.digitalbanking.embedded.CustomerSecQuestion;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityQuestions;
import com.mob.casestudy.digitalbanking.entity.SecurityQuestions;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

public class CustomerSecurityQuestionsDto {

    private UUID securityQuestionId;

    private String securityQuestionText;

    @NotNull(message = "SecurityQuestionAnswer should not be Null")
    @NotEmpty(message = "SecurityQuestionAnswer should not be Empty")
    @Size(min = 3, message = "SecurityQuestionAnswer should be minimum 3 Characters")
    private String securityQuestionAnswer;

    public CustomerSecurityQuestionsDto() {
    }

    public CustomerSecurityQuestionsDto(UUID securityQuestionId, String securityQuestionText, String securityQuestionAnswer) {
        this.securityQuestionId = securityQuestionId;
        this.securityQuestionText = securityQuestionText;
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public UUID getSecurityQuestionId() {
        return securityQuestionId;
    }

    public String getSecurityQuestionText() {
        return securityQuestionText;
    }

    public String getSecurityQuestionAnswer() {
        return securityQuestionAnswer;
    }
}
