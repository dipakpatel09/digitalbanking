package com.mob.casestudy.digitalbanking.entity;

import com.mob.casestudy.digitalbanking.embedded.CustomerOTPId;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
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

    public CustomerOTP() {
    }

    public CustomerOTP(String otpMessage, String otp, Integer otpRetries, LocalDateTime expiryOn, LocalDateTime createdOn) {
        this.otpMessage = otpMessage;
        this.otp = otp;
        this.otpRetries = otpRetries;
        this.expiryOn = expiryOn;
        this.createdOn = createdOn;
    }

    public CustomerOTP(CustomerOTPId customerOTPId, String otpMessage, String otp, Integer otpRetries, LocalDateTime expiryOn, LocalDateTime createdOn) {
        this.customerOTPId = customerOTPId;
        this.otpMessage = otpMessage;
        this.otp = otp;
        this.otpRetries = otpRetries;
        this.expiryOn = expiryOn;
        this.createdOn = createdOn;
    }

    public CustomerOTPId getCustomerOTPId() {
        return customerOTPId;
    }

    public void setCustomerOTPId(CustomerOTPId customerOTPId) {
        this.customerOTPId = customerOTPId;
    }

    public String getOtpMessage() {
        return otpMessage;
    }

    public void setOtpMessage(String otpMessage) {
        this.otpMessage = otpMessage;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Integer getOtpRetries() {
        return otpRetries;
    }

    public void setOtpRetries(Integer otpRetries) {
        this.otpRetries = otpRetries;
    }

    public LocalDateTime getExpiryOn() {
        return expiryOn;
    }

    public void setExpiryOn(LocalDateTime expiryOn) {
        this.expiryOn = expiryOn;
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
}
