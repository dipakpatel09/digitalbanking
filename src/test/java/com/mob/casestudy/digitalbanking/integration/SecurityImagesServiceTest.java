package com.mob.casestudy.digitalbanking.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digitalbanking.openapi.model.GetSecurityImagesResponse;
import com.digitalbanking.openapi.model.SecurityImage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mob.casestudy.digitalbanking.DigitalbankingApplication;
import com.mob.casestudy.digitalbanking.customerror.ErrorList;
import com.mob.casestudy.digitalbanking.entity.*;
import com.mob.casestudy.digitalbanking.exceptionhandler.ExceptionResponse;
import com.mob.casestudy.digitalbanking.mapper.SecurityImagesMapperImpl;
import com.mob.casestudy.digitalbanking.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DigitalbankingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(value = "classpath:application.yml")
@AutoConfigureMockMvc
class SecurityImagesServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    SecurityImagesRepo securityImagesRepo;

    @Autowired
    CustomerSecurityImagesRepo customerSecurityImagesRepo;

    @Autowired
    SecurityImagesMapperImpl securityImagesMapper;

    @BeforeEach
    void setUp() {
        securityImagesRepo.save(SecurityImages.builder().securityImageName("What is your favourite Logo?").securityImageURL("http://CustomerLogo").build());
        securityImagesRepo.save(SecurityImages.builder().securityImageName("What is your favourite TV show?").securityImageURL("http://CustomerTVshow").build());
    }

    @Test
    void retrieveSecurityImages_withEmptySecurityImagesList_shouldThrowException() throws Exception {
        customerSecurityImagesRepo.deleteAll();
        securityImagesRepo.deleteAll();

        ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorList.SEC_IMG_NOT_FOUND, "Security images not found...");
        String exceptionResponseMapper = new ObjectMapper().writeValueAsString(exceptionResponse);
        this.mockMvc.perform(get("/customer-service/service-api/v2/securityImages"))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isNotFound());
    }

    @Test
    void retrieveSecurityImages_withValidSecurityImagesList_shouldReturnSecurityImagesList() throws Exception {
        List<SecurityImages> securityImages = securityImagesRepo.findAll();
        List<SecurityImage> securityImageList = securityImages.stream().map(securityImagesMapper::toDto).toList();
        GetSecurityImagesResponse securityImagesResponse = new GetSecurityImagesResponse();
        securityImagesResponse.setSecurityImages(securityImageList);
        String securityImagesResponseMapper = new ObjectMapper().writeValueAsString(securityImagesResponse);
        this.mockMvc.perform(get("/customer-service/service-api/v2/securityImages"))
                .andExpect(content().json(securityImagesResponseMapper))
                .andExpect(status().isOk());
    }
}
