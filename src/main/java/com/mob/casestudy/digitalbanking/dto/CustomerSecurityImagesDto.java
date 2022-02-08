package com.mob.casestudy.digitalbanking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class CustomerSecurityImagesDto {

    private UUID securityImageId;
    private String securityImageName;
    private String securityImageCaption;
    private String securityImageUrl;

    public CustomerSecurityImagesDto(UUID securityImageId, String securityImageName, String securityImageCaption, String securityImageUrl) {
        this.securityImageId = securityImageId;
        this.securityImageName = securityImageName;
        this.securityImageCaption = securityImageCaption;
        this.securityImageUrl = securityImageUrl;
    }
}
