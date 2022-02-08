package com.mob.casestudy.digitalbanking.dto;

import java.util.List;
public class GetSecurityImagesResponse {

    private List<SecurityImagesDto> securityImages;

    public GetSecurityImagesResponse() {
    }

    public GetSecurityImagesResponse(List<SecurityImagesDto> securityImages) {
        this.securityImages = securityImages;
    }

    public List<SecurityImagesDto> getSecurityImages() {
        return securityImages;
    }
}
