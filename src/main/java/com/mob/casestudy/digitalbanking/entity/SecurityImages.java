package com.mob.casestudy.digitalbanking.entity;

import com.mob.casestudy.digitalbanking.dto.SecurityImagesDto;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class SecurityImages {

    @Id
    @Column(length = 36)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(length = 50)
    private String securityImageName;

    @Column(length = 254)
    private String securityImageURL;

    @OneToMany(mappedBy = "securityImages")
    private List<CustomerSecurityImages> customerSecurityImages;

    public SecurityImages() {
    }

    public SecurityImages(String securityImageName, String securityImageURL) {
        this.securityImageName = securityImageName;
        this.securityImageURL = securityImageURL;
    }

    public SecurityImages(UUID id, String securityImageName, String securityImageURL) {
        this.id = id;
        this.securityImageName = securityImageName;
        this.securityImageURL = securityImageURL;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSecurityImageName() {
        return securityImageName;
    }

    public void setSecurityImageName(String securityImageName) {
        this.securityImageName = securityImageName;
    }

    public String getSecurityImageURL() {
        return securityImageURL;
    }

    public void setSecurityImageURL(String securityImageURL) {
        this.securityImageURL = securityImageURL;
    }

    public SecurityImagesDto toDto() {
        return new SecurityImagesDto(id, securityImageName, securityImageURL);
    }
}
