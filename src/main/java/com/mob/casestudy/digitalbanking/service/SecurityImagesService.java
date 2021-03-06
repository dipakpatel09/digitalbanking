package com.mob.casestudy.digitalbanking.service;

import static com.mob.casestudy.digitalbanking.errorlist.CustomError.*;

import com.mob.casestudy.digitalbanking.dto.GetSecurityImagesResponse;
import com.mob.casestudy.digitalbanking.entity.SecurityImages;
import com.mob.casestudy.digitalbanking.exception.CustomNotFoundException;
import com.mob.casestudy.digitalbanking.repository.SecurityImagesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityImagesService {

    @Autowired
    SecurityImagesRepo securityImagesRepo;

    public GetSecurityImagesResponse getSecurityImages() {
        List<SecurityImages> securityImages = securityImagesRepo.findAll();
        if (securityImages.isEmpty()) {
            throw new CustomNotFoundException(SEC_IMG_NOT_FOUND, "Security images not found...");
        }
        return new GetSecurityImagesResponse(securityImages.stream().map(SecurityImages::toDto).toList());
    }
}
