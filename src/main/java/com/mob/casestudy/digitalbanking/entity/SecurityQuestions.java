package com.mob.casestudy.digitalbanking.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class SecurityQuestions {

    @Id
    @Column(length = 36)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(length = 50)
    private String securityQuestionText;

    @OneToMany(mappedBy = "securityQuestions")
    private List<CustomerSecurityQuestions> customerSecurityQuestions;

    public SecurityQuestions() {
    }

    public SecurityQuestions(String securityQuestionText) {
        this.securityQuestionText = securityQuestionText;
    }

    public SecurityQuestions(UUID id, String securityQuestionText) {
        this.id = id;
        this.securityQuestionText = securityQuestionText;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSecurityQuestionText() {
        return securityQuestionText;
    }

    public void setSecurityQuestionText(String securityQuestionText) {
        this.securityQuestionText = securityQuestionText;
    }
}
