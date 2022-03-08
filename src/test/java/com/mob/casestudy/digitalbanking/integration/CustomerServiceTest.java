package com.mob.casestudy.digitalbanking.integration;

import static com.mob.casestudy.digitalbanking.customerror.ErrorList.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.*;

import com.digitalbanking.openapi.model.CreateCustomerRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mob.casestudy.digitalbanking.DigitalbankingApplication;
import com.mob.casestudy.digitalbanking.entity.*;
import com.mob.casestudy.digitalbanking.enumrator.Language;
import com.mob.casestudy.digitalbanking.enumrator.Status;
import com.mob.casestudy.digitalbanking.exceptionhandler.ExceptionResponse;
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
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DigitalbankingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(value = "classpath:application.yml")
@AutoConfigureMockMvc
class CustomerServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CustomerRepo customerRepo;

    @BeforeEach
    void setUp() {
        Customer customer = Customer.builder().userName("Dipak").firstName("Dipak").lastName("Patel").phoneNumber("0123456789").email("abc@gmail.com").status(Status.ACTIVE).preferredLanguage(Language.EN).externalId("dipak007").createdBy("Dipak1").createdOn(LocalDateTime.now()).updatedBy("Dipak2").updatedOn(LocalDateTime.now()).build();
        customerRepo.save(customer);
    }

    @AfterEach
    void tearDown() {
        customerRepo.deleteAll();
    }

    @Test
    void deleteCustomer_withInvalidCustomer_shouldThrowException() throws Exception {
        String name = "abc";
        ExceptionResponse exceptionResponse = new ExceptionResponse(CUS_DELETE_NOT_FOUND, "Invalid User.. " + name);
        String exceptionResponseMapper = new ObjectMapper().writeValueAsString(exceptionResponse);
        this.mockMvc.perform(delete("/customer-service/client-api/v1/customers/" + name))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteCustomer_withValidCustomer_shouldDeleteCustomer() throws Exception {
        this.mockMvc.perform(delete("/customer-service/client-api/v1/customers/Dipak")).andExpect(status().isNoContent());
    }

    @Test
    void createCustomer_withInvalidPhoneNumber_shouldThrowException() throws Exception {
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.setUserName("Dipak08");
        createCustomerRequest.setFirstName("Dipak");
        createCustomerRequest.setLastName("Patel");
        createCustomerRequest.setPhoneNumber("1234567");
        ExceptionResponse exceptionResponse = new ExceptionResponse(PHONE_NO_INVALID_ERROR, "Phone no must be of 10 digit");
        String exceptionResponseMapper = new ObjectMapper().writeValueAsString(exceptionResponse);
        String createCustomerRequestMapper = new ObjectMapper().writeValueAsString(createCustomerRequest);
        this.mockMvc.perform(post("/customer-service/client-api/v1/customers").contentType(APPLICATION_JSON).content(createCustomerRequestMapper))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCustomer_withInvalidEmail_shouldThrowException() throws Exception {
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.setUserName("Dipak08");
        createCustomerRequest.setFirstName("Dipak");
        createCustomerRequest.setLastName("Patel");
        createCustomerRequest.setPhoneNumber("0123456789");
        createCustomerRequest.setEmail("abc.xyz@");
        ExceptionResponse exceptionResponse = new ExceptionResponse(EMAIL_ADDRESS_INVALID_ERROR, "Invalid Email");
        String exceptionResponseMapper = new ObjectMapper().writeValueAsString(exceptionResponse);
        String createCustomerRequestMapper = new ObjectMapper().writeValueAsString(createCustomerRequest);
        this.mockMvc.perform(post("/customer-service/client-api/v1/customers").contentType(APPLICATION_JSON).content(createCustomerRequestMapper))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCustomer_withInvalidUserName_shouldThrowException() throws Exception {
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.setUserName("Dpk");
        createCustomerRequest.setFirstName("Dipak");
        createCustomerRequest.setLastName("Patel");
        createCustomerRequest.setPhoneNumber("0123456789");
        createCustomerRequest.setEmail("abc.xyz@gmail.com");
        ExceptionResponse exceptionResponse = new ExceptionResponse(USER_NAME_FORMAT_ERROR, "User name is in not valid format");
        String exceptionResponseMapper = new ObjectMapper().writeValueAsString(exceptionResponse);
        String createCustomerRequestMapper = new ObjectMapper().writeValueAsString(createCustomerRequest);
        this.mockMvc.perform(post("/customer-service/client-api/v1/customers").contentType(APPLICATION_JSON).content(createCustomerRequestMapper))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCustomer_withExistingCustomer_shouldThrowException() throws Exception {
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.setUserName("Dipak");
        createCustomerRequest.setFirstName("Dipak");
        createCustomerRequest.setLastName("Patel");
        createCustomerRequest.setPhoneNumber("0123456789");
        createCustomerRequest.setEmail("abc.xyz@gmail.com");
        ExceptionResponse exceptionResponse = new ExceptionResponse(USER_NAME_NOT_UNIQUE_ERROR, "This user is already in the table");
        String exceptionResponseMapper = new ObjectMapper().writeValueAsString(exceptionResponse);
        String createCustomerRequestMapper = new ObjectMapper().writeValueAsString(createCustomerRequest);
        this.mockMvc.perform(post("/customer-service/client-api/v1/customers").contentType(APPLICATION_JSON).content(createCustomerRequestMapper))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createCustomer_withValidCustomerRequest_shouldCreateCustomer() throws Exception {
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        createCustomerRequest.setUserName("Dipak08");
        createCustomerRequest.setFirstName("Dipak");
        createCustomerRequest.setLastName("Patel");
        createCustomerRequest.setPhoneNumber("0123456789");
        createCustomerRequest.setEmail("abc.xyz@gmail.com");
        String createCustomerRequestMapper = new ObjectMapper().writeValueAsString(createCustomerRequest);
        this.mockMvc.perform(post("/customer-service/client-api/v1/customers").contentType(APPLICATION_JSON).content(createCustomerRequestMapper))
                .andExpect(status().isOk());
    }

    @Test
    void retrieveCustomer_withNullOrEmptyIdAndNullOrEmptyUserName_shouldThrowException() throws Exception {
        ExceptionResponse exceptionResponse = new ExceptionResponse(CUS_FIELD_VALIDATION_ERROR, "Customer field validation failed");
        String exceptionResponseMapper = new ObjectMapper().writeValueAsString(exceptionResponse);
        this.mockMvc.perform(get("/customer-service/client-api/v1/customers").param("id", "").param("user_name", ""))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isBadRequest());
    }

    @Test
    void retrieveCustomer_withInvalidId_shouldThrowException() throws Exception {
        ExceptionResponse exceptionResponse = new ExceptionResponse(CUS_NOT_FOUND_ERROR, CUS_NOT_FOUND_DESCRIPTION);
        String exceptionResponseMapper = new ObjectMapper().writeValueAsString(exceptionResponse);
        this.mockMvc.perform(get("/customer-service/client-api/v1/customers").param("id", "855c61f9-8067-4e93-9a1f-90128442453c").param("user_name", ""))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isNotFound());
    }

    @Test
    void retrieveCustomer_withInvalidUserName_shouldThrowException() throws Exception {
        ExceptionResponse exceptionResponse = new ExceptionResponse(CUS_NOT_FOUND_ERROR, CUS_NOT_FOUND_DESCRIPTION);
        String exceptionResponseMapper = new ObjectMapper().writeValueAsString(exceptionResponse);
        this.mockMvc.perform(get("/customer-service/client-api/v1/customers").param("id", "").param("user_name", "Dipak08"))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isNotFound());
    }

    @Test
    void retrieveCustomer_withEmptyCustomerList_shouldThrowException() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "Dipak";
        List<Customer> customerList = customerRepo.findByIdOrUserName(id, name);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules().writeValueAsString(customerList);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String objectMapperResponse = objectMapper.writeValueAsString(objectMapper);
        this.mockMvc.perform(get("/customer-service/client-api/v1/customers").param("id", id.toString()).param("user_name", name))
                .andExpect(content().json(objectMapperResponse))
                .andExpect(status().isOk());
    }
}
