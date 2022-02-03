package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.dto.GetSecurityImagesResponse;
import com.mob.casestudy.digitalbanking.dto.SecurityImagesDto;
import com.mob.casestudy.digitalbanking.entity.SecurityImages;
import com.mob.casestudy.digitalbanking.exception.SecurityImagesNotFoundException;
import com.mob.casestudy.digitalbanking.repository.SecurityImagesRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class SecurityImagesServiceTest {

    @InjectMocks
    SecurityImagesService securityImagesService;

    @Mock
    SecurityImagesRepo securityImagesRepo;

    @Test
    void getSecurityImages_withEmptySecurityImagesList_shouldThrowException() {
        Mockito.when(securityImagesRepo.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertThrows(SecurityImagesNotFoundException.class, () -> securityImagesService.getSecurityImages());
    }

    @Test
    void getSecurityImages_withValidSecurityImagesList_shouldReturnSecurityImagesList() {
        List<SecurityImages> securityImagesList=new ArrayList<>();
        SecurityImages securityImages=new SecurityImages("What is your favourite TV show?","http://CustomerTVshow");
        securityImagesList.add(securityImages);
        List<SecurityImagesDto> securityImagesDtoList=List.of(securityImages.toDto());
        GetSecurityImagesResponse expected=new GetSecurityImagesResponse(securityImagesDtoList);
        Mockito.when(securityImagesRepo.findAll()).thenReturn(securityImagesList);
        GetSecurityImagesResponse actual = securityImagesService.getSecurityImages();
        org.assertj.core.api.Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}