package com.mob.casestudy.digitalbanking.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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
}
