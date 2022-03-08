package com.mob.casestudy.digitalbanking.entity;

import com.mob.casestudy.digitalbanking.embedded.CustomerOTPId;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerOTP {

    @EmbeddedId
    private CustomerOTPId customerOTPId = new CustomerOTPId();

    @Column(length = 160)
    private String otpMessage;

    @Column(length = 6)
    private String otp;

    @Column(scale = 1)
    private Integer otpRetries;

    private LocalDateTime expiryOn;

    private LocalDateTime createdOn;

    @OneToOne
    @MapsId("customerId")
    private Customer customer;
}
