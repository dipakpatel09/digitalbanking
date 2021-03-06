package com.mob.casestudy.digitalbanking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class SecurityImagesDto {

    private UUID securityImageId;
    private String securityImageName;
    private String securityImageUrl;

    public SecurityImagesDto(UUID securityImageId, String securityImageName, String securityImageUrl) {
        this.securityImageId = securityImageId;
        this.securityImageName = securityImageName;
        this.securityImageUrl = securityImageUrl;
    }
}
