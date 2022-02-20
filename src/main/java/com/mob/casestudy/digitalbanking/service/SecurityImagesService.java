package com.mob.casestudy.digitalbanking.service;

import com.digitalbanking.openapi.model.GetSecurityImagesResponse;
import com.digitalbanking.openapi.model.SecurityImage;
import com.mob.casestudy.digitalbanking.customerror.ErrorList;
import com.mob.casestudy.digitalbanking.entity.SecurityImages;
import com.mob.casestudy.digitalbanking.exception.CustomNotFoundException;
import com.mob.casestudy.digitalbanking.mapper.SecurityImagesMapperImpl;
import com.mob.casestudy.digitalbanking.repository.SecurityImagesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityImagesService {

    @Autowired
    SecurityImagesRepo securityImagesRepo;

    @Autowired
    SecurityImagesMapperImpl securityImagesMapper;

    public ResponseEntity<GetSecurityImagesResponse> retrieveSecurityImages() {
        List<SecurityImages> securityImages = securityImagesRepo.findAll();
        if (securityImages.isEmpty()) {
            throw new CustomNotFoundException(ErrorList.SEC_IMG_NOT_FOUND, "Security images not found...");
        }
        List<SecurityImage> securityImageList = securityImages.stream().map(securityImagesMapper::toDto).toList();
        return ResponseEntity.ok().body(new GetSecurityImagesResponse().securityImages(securityImageList));
    }
}
