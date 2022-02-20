package com.mob.casestudy.digitalbanking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mob.casestudy.digitalbanking.enumrator.Language;
import com.mob.casestudy.digitalbanking.enumrator.Status;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Customer {

    @Id
    @Column(length = 36)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(length = 30)
    private String userName;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(length = 10)
    private String phoneNumber;

    @Column(length = 50)
    private String email;

    @Column(length = 3)
    private String age;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(length = 2)
    @Enumerated(EnumType.STRING)
    private Language preferredLanguage;

    @Column(length = 50)
    private String externalId;

    @Column(length = 50)
    private String createdBy;

    private LocalDateTime createdOn;

    @Column(length = 50)
    private String updatedBy;

    private LocalDateTime updatedOn;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private final List<CustomerSecurityQuestions> customerSecurityQuestions = new ArrayList<>();

    @OneToOne(mappedBy = "customer", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private CustomerSecurityImages customerSecurityImages;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private CustomerOTP customerOTP;

    public void addCustomerSecurityQuestions(CustomerSecurityQuestions customerSecurityQuestions) {
        this.customerSecurityQuestions.add(customerSecurityQuestions);
    }

    public void removeCustomerSecurityQuestions(CustomerSecurityQuestions customerSecurityQuestions) {
        this.customerSecurityQuestions.remove(customerSecurityQuestions);
    }

}
