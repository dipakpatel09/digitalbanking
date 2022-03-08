package com.mob.casestudy.digitalbanking.entity;

import com.mob.casestudy.digitalbanking.embedded.CustomerSecQuestion;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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
}
