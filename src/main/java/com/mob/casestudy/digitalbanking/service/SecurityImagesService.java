package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.dto.SecurityImagesDto;
import com.mob.casestudy.digitalbanking.dto.GetSecurityImagesResponse;
import com.mob.casestudy.digitalbanking.entity.SecurityImages;
import com.mob.casestudy.digitalbanking.exception.SecurityImagesNotFoundException;
import com.mob.casestudy.digitalbanking.repository.SecurityImagesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityImagesService {

    @Autowired
    SecurityImagesRepo securityImagesRepo;

    public GetSecurityImagesResponse getSecurityImages() {
        List<SecurityImages> securityImagesList = securityImagesRepo.findAll();
        if (securityImagesList.isEmpty()) {
            throw new SecurityImagesNotFoundException("Security images not found...");
        }
        List<SecurityImagesDto> securityImagesDtoList = securityImagesList.stream().map(SecurityImages::toDto).toList();
        return new GetSecurityImagesResponse(securityImagesDtoList);
    }
}
