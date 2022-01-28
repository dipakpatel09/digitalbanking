package com.mob.casestudy.digitalbanking.entity;

import com.mob.casestudy.digitalbanking.dto.CustomerSecurityQuestionsDto;
import com.mob.casestudy.digitalbanking.embedded.CustomerSecQuestion;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class CustomerSecurityQuestions {

    @EmbeddedId
    private CustomerSecQuestion customerSecQuestion = new CustomerSecQuestion();

    @Column(length = 50)
    private String securityQuestionAnswer;

    @Column(length = 50)
    private LocalDateTime createdOn;

    @ManyToOne
    @MapsId("customerId")
    private Customer customer;

    @ManyToOne
    @MapsId("securityQuestionId")
    private SecurityQuestions securityQuestions;

    public CustomerSecurityQuestions() {
    }

    public CustomerSecurityQuestions(String securityQuestionAnswer, LocalDateTime createdOn) {
        this.customerSecQuestion = new CustomerSecQuestion();
        this.securityQuestionAnswer = securityQuestionAnswer;
        this.createdOn = createdOn;
    }

    public CustomerSecurityQuestions(CustomerSecQuestion customerSecQuestion, String securityQuestionAnswer, LocalDateTime createdOn) {
        this.customerSecQuestion = customerSecQuestion;
        this.securityQuestionAnswer = securityQuestionAnswer;
        this.createdOn = createdOn;
    }

    public CustomerSecurityQuestions(CustomerSecQuestion customerSecQuestion, String securityQuestionAnswer) {
        this.customerSecQuestion=customerSecQuestion;
        this.securityQuestionAnswer=securityQuestionAnswer;
    }

    public CustomerSecQuestion getCustomerSecQuestion() {
        return customerSecQuestion;
    }

    public void setCustomerSecQuestion(CustomerSecQuestion customerSecQuestion) {
        this.customerSecQuestion = customerSecQuestion;
    }

    public String getSecurityQuestionAnswer() {
        return securityQuestionAnswer;
    }

    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public SecurityQuestions getSecurityQuestions() {
        return securityQuestions;
    }

    public void setSecurityQuestions(SecurityQuestions securityQuestions) {
        this.securityQuestions = securityQuestions;
    }

    public CustomerSecurityQuestionsDto toDto() {

        return new CustomerSecurityQuestionsDto(securityQuestions.getId(), securityQuestions.getSecurityQuestionText(), securityQuestionAnswer);
    }
}
