package com.mob.casestudy.digitalbanking.dto;

import java.util.UUID;

public class SecurityImagesDto {

    private UUID securityImageId;
    private String securityImageName;
    private String securityImageUrl;

    public SecurityImagesDto() {
    }

    public SecurityImagesDto(UUID securityImageId, String securityImageName, String securityImageUrl) {
        this.securityImageId = securityImageId;
        this.securityImageName = securityImageName;
        this.securityImageUrl = securityImageUrl;
    }

    public UUID getSecurityImageId() {
        return securityImageId;
    }

    public String getSecurityImageName() {
        return securityImageName;
    }

    public String getSecurityImageUrl() {
        return securityImageUrl;
    }
}
