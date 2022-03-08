package com.mob.casestudy.digitalbanking.integration;

import static com.mob.casestudy.digitalbanking.customerror.ErrorList.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digitalbanking.openapi.model.GetCustomerSecurityImageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mob.casestudy.digitalbanking.DigitalbankingApplication;
import com.mob.casestudy.digitalbanking.embedded.CustomerSecImage;
import com.mob.casestudy.digitalbanking.entity.*;
import com.mob.casestudy.digitalbanking.enumrator.Language;
import com.mob.casestudy.digitalbanking.enumrator.Status;
import com.mob.casestudy.digitalbanking.exceptionhandler.ExceptionResponse;
import com.mob.casestudy.digitalbanking.mapper.CustomerSecurityImagesMapperImpl;
import com.mob.casestudy.digitalbanking.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DigitalbankingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(value = "classpath:application.yml")
@AutoConfigureMockMvc
class CustomerSecurityImagesServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    CustomerSecurityImagesRepo customerSecurityImagesRepo;

    @Autowired
    SecurityImagesRepo securityImagesRepo;

    @Autowired
    CustomerSecurityImagesMapperImpl customerSecurityImagesMapper;

    @BeforeEach
    void setUp() {
        Customer customer = Customer.builder().userName("Dipak").firstName("Dipak").lastName("Patel").phoneNumber("0123456789").email("abc@gmail.com").status(Status.ACTIVE).preferredLanguage(Language.EN).externalId("dipak007").createdBy("Dipak1").createdOn(LocalDateTime.now()).updatedBy("Dipak2").updatedOn(LocalDateTime.now()).build();
        customerRepo.save(customer);

        CustomerSecurityImages customerSecurityImages = CustomerSecurityImages.builder().customerSecImage(new CustomerSecImage()).securityImageCaption("Apple").createdOn(LocalDateTime.now()).build();

        SecurityImages images = securityImagesRepo.save(SecurityImages.builder().securityImageName("What is your favourite Logo?").securityImageURL("http://CustomerLogo").build());
        securityImagesRepo.save(SecurityImages.builder().securityImageName("What is your favourite TV show?").securityImageURL("http://CustomerTVshow").build());

        customerSecurityImages.setCustomer(customer);
        customerSecurityImages.setSecurityImages(images);
        customerSecurityImagesRepo.save(customerSecurityImages);
    }

    @AfterEach
    void tearDown() {
        customerSecurityImagesRepo.deleteAll();
        securityImagesRepo.deleteAll();
        customerRepo.deleteAll();
    }

    @Test
    void retrieveSecurityImageByUserName_withInvalidCustomer_shouldThrowException() throws Exception {
        String name = "abc";
        ExceptionResponse exceptionResponse = new ExceptionResponse(CUS_SEC_IMG_CUS_NOT_FOUND, "User " + name + " not found");
        String exceptionResponseMapper = new ObjectMapper().writeValueAsString(exceptionResponse);
        this.mockMvc.perform(get("/customer-service/client-api/v1/customers/" + name + "/securityImages"))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isNotFound());
    }

    @Test
    void retrieveSecurityImageByUserName_withNullCustomerSecurityImage_shouldThrowException() throws Exception {
        String name = "abc";
        Customer customer = new Customer();
        customer.setUserName(name);
        customerRepo.save(customer);

        ExceptionResponse exceptionResponse = new ExceptionResponse(CUS_SEC_IMG_NOT_FOUND, "Images not found");
        String exceptionResponseMapper = new ObjectMapper().writeValueAsString(exceptionResponse);
        this.mockMvc.perform(get("/customer-service/client-api/v1/customers/" + name + "/securityImages"))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isNotFound());
    }

    @Test
    void retrieveSecurityImageByUserName_withValidCustomer_shouldReturnCustomerSecurityImage() throws Exception {
        String name = "Dipak";
        Customer customer = customerRepo.findByUserName(name).get();
        CustomerSecurityImages customerSecurityImages = customer.getCustomerSecurityImages();
        GetCustomerSecurityImageResponse customerSecurityImageResponse = customerSecurityImagesMapper.toDto(customerSecurityImages);
        String customerSecurityImageResponseMapper = new ObjectMapper().writeValueAsString(customerSecurityImageResponse);
        this.mockMvc.perform(get("/customer-service/client-api/v1/customers/" + name + "/securityImages"))
                .andExpect(content().json(customerSecurityImageResponseMapper))
                .andExpect(status().isOk());
    }
}
