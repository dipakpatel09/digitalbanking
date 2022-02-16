package com.mob.casestudy.digitalbanking.service;

import static com.mob.casestudy.digitalbanking.customerror.ErrorList.*;

import com.digitalbanking.openapi.model.GetSecurityImagesResponse;
import com.digitalbanking.openapi.model.SecurityImage;
import com.mob.casestudy.digitalbanking.entity.SecurityImages;
import com.mob.casestudy.digitalbanking.exception.CustomNotFoundException;
import com.mob.casestudy.digitalbanking.repository.SecurityImagesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityImagesService {

    @Autowired
    SecurityImagesRepo securityImagesRepo;

    public ResponseEntity<GetSecurityImagesResponse> retrieveSecurityImages() {
        List<SecurityImages> securityImages = securityImagesRepo.findAll();
        if (securityImages.isEmpty()) {
            throw new CustomNotFoundException(SEC_IMG_NOT_FOUND, "Security images not found...");
        }
        List<SecurityImage> securityImageList = securityImages.stream().map(this::toDto).toList();
        return ResponseEntity.ok().body(new GetSecurityImagesResponse().securityImages(securityImageList));
    }

    private SecurityImage toDto(SecurityImages securityImages) {
        return new SecurityImage().securityImageId(securityImages.getId().toString())
                .securityImageName(securityImages.getSecurityImageName())
                .securityImageUrl(securityImages.getSecurityImageURL());
    }
}
