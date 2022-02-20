package com.mob.casestudy.digitalbanking.service;

import com.digitalbanking.openapi.model.GetSecurityImagesResponse;
import com.digitalbanking.openapi.model.SecurityImage;
import com.mob.casestudy.digitalbanking.entity.SecurityImages;
import com.mob.casestudy.digitalbanking.exception.CustomNotFoundException;
import com.mob.casestudy.digitalbanking.mapper.SecurityImagesMapperImpl;
import com.mob.casestudy.digitalbanking.repository.SecurityImagesRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class SecurityImagesServiceTest {

    @InjectMocks
    SecurityImagesService securityImagesService;

    @Mock
    SecurityImagesRepo securityImagesRepo;

    @Mock
    SecurityImagesMapperImpl securityImagesMapper;

    @Test
    void getSecurityImages_withEmptySecurityImagesList_shouldThrowException() {
        Mockito.when(securityImagesRepo.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertThrows(CustomNotFoundException.class, () -> securityImagesService.retrieveSecurityImages());
    }

    @Test
    void getSecurityImages_withValidSecurityImagesList_shouldReturnSecurityImagesList() {
        UUID uuid = UUID.randomUUID();
        SecurityImages images = new SecurityImages();
        images.setId(uuid);
        images.setSecurityImageName("What is your favourite TV show?");
        images.setSecurityImageURL("http://CustomerTVshow");
        SecurityImage securityImage = new SecurityImage().securityImageId(uuid.toString()).securityImageName("What is your favourite TV show?").securityImageUrl("http://CustomerTVshow");
        List<SecurityImage> securityImageList = List.of(securityImage);
        List<SecurityImages> securityImages = new ArrayList<>();
        securityImages.add(images);
        Mockito.when(securityImagesRepo.findAll()).thenReturn(securityImages);
        Mockito.when(securityImagesMapper.toDto(images)).thenReturn(securityImage);
        ResponseEntity<GetSecurityImagesResponse> expected = ResponseEntity.ok().body(new GetSecurityImagesResponse().securityImages(securityImageList));
        ResponseEntity<GetSecurityImagesResponse> actual = securityImagesService.retrieveSecurityImages();
        org.assertj.core.api.Assertions.assertThat(expected).usingRecursiveComparison().isEqualTo(actual);
    }
}