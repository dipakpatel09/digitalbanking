package com.mob.casestudy.digitalbanking.entity;

import com.mob.casestudy.digitalbanking.embedded.CustomerSecImage;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CustomerSecurityImages {

    @EmbeddedId
    private CustomerSecImage customerSecImage = new CustomerSecImage();

    @Column(length = 50)
    private String securityImageCaption;

    @Column(length = 50)
    private LocalDateTime createdOn;

    @OneToOne
    @MapsId("customerId")
    private Customer customer;

    @ManyToOne
    @MapsId("securityImageId")
    private SecurityImages securityImages;

    public CustomerSecurityImages() {
    }

    public CustomerSecurityImages(String securityImageCaption, LocalDateTime createdOn) {
        this.securityImageCaption = securityImageCaption;
        this.createdOn = createdOn;
    }

    public CustomerSecurityImages(CustomerSecImage customerSecImage, String securityImageCaption, LocalDateTime createdOn) {
        this.customerSecImage = customerSecImage;
        this.securityImageCaption = securityImageCaption;
        this.createdOn = createdOn;
    }

    public CustomerSecImage getCustomerSecImage() {
        return customerSecImage;
    }

    public void setCustomerSecImage(CustomerSecImage customerSecImage) {
        this.customerSecImage = customerSecImage;
    }

    public String getSecurityImageCaption() {
        return securityImageCaption;
    }

    public void setSecurityImageCaption(String securityImageCaption) {
        this.securityImageCaption = securityImageCaption;
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

    public SecurityImages getSecurityImages() {
        return securityImages;
    }

    public void setSecurityImages(SecurityImages securityImages) {
        this.securityImages = securityImages;
    }
}
