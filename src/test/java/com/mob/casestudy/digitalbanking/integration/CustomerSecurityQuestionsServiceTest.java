package com.mob.casestudy.digitalbanking.integration;

import static com.mob.casestudy.digitalbanking.customerror.ErrorList.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digitalbanking.openapi.model.CreateCustomerSecurityQuestionsRequest;
import com.digitalbanking.openapi.model.SecurityQuestion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mob.casestudy.digitalbanking.DigitalbankingApplication;
import com.mob.casestudy.digitalbanking.embedded.CustomerSecQuestion;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DigitalbankingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(value = "classpath:application.yml")
@AutoConfigureMockMvc
class CustomerSecurityQuestionsServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    CustomerSecurityQuestionsRepo customerSecurityQuestionsRepo;

    @Autowired
    SecurityQuestionsRepo securityQuestionsRepo;

    @BeforeEach
    void setUp() {
        Customer customer = Customer.builder().userName("Dipak").firstName("Dipak").lastName("Patel").phoneNumber("0123456789").email("abc@gmail.com").status(Status.ACTIVE).preferredLanguage(Language.EN).externalId("dipak007").createdBy("Dipak1").createdOn(LocalDateTime.now()).updatedBy("Dipak2").updatedOn(LocalDateTime.now()).build();
        customerRepo.save(customer);
        CustomerSecurityQuestions customerSecurityQuestions = CustomerSecurityQuestions.builder().customerSecQuestion(new CustomerSecQuestion()).securityQuestionAnswer("Iphone").createdOn(LocalDateTime.now()).build();
        CustomerSecurityQuestions customerSecurityQuestions1 = CustomerSecurityQuestions.builder().customerSecQuestion(new CustomerSecQuestion()).securityQuestionAnswer("Ahmedabad").createdOn(LocalDateTime.now()).build();
        CustomerSecurityQuestions customerSecurityQuestions2 = CustomerSecurityQuestions.builder().customerSecQuestion(new CustomerSecQuestion()).securityQuestionAnswer("Red").createdOn(LocalDateTime.now()).build();

        SecurityQuestions questions = securityQuestionsRepo.save(SecurityQuestions.builder().securityQuestionText("What is your favourite Phone?").build());
        SecurityQuestions questions1 = securityQuestionsRepo.save(SecurityQuestions.builder().securityQuestionText("What is your favourite Place?").build());
        SecurityQuestions questions2 = securityQuestionsRepo.save(SecurityQuestions.builder().securityQuestionText("What is your favourite Color?").build());
        securityQuestionsRepo.save(SecurityQuestions.builder().securityQuestionText("What is your favourite Food?").build());

        customerSecurityQuestions.setCustomer(customer);
        customerSecurityQuestions1.setCustomer(customer);
        customerSecurityQuestions2.setCustomer(customer);
        customerSecurityQuestions.setSecurityQuestions(questions);
        customerSecurityQuestions1.setSecurityQuestions(questions1);
        customerSecurityQuestions2.setSecurityQuestions(questions2);

        customerSecurityQuestionsRepo.save(customerSecurityQuestions);
        customerSecurityQuestionsRepo.save(customerSecurityQuestions1);
        customerSecurityQuestionsRepo.save(customerSecurityQuestions2);
    }

    @AfterEach
    void tearDown() {
        customerSecurityQuestionsRepo.deleteAll();
        securityQuestionsRepo.deleteAll();
        customerRepo.deleteAll();
    }

    @Test
    void createSecurityQuestions_withInvalidCustomer_shouldThrowException() throws Exception {
        String name = "abc";
        ExceptionResponse exceptionResponse = new ExceptionResponse(CUS_SEC_QUES_CUS_NOT_FOUND, "The requested user not found.. " + name);
        String exceptionResponseMapper = new ObjectMapper().writeValueAsString(exceptionResponse);
        this.mockMvc.perform(put("/customer-service/client-api/v1/customers/" + name + "/securityQuestions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isNotFound());
    }

    @Test
    void createSecurityQuestions_withEmptySecurityQuestionList_shouldThrowException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<SecurityQuestion> securityQuestion = new ArrayList<>();
        CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest = new CreateCustomerSecurityQuestionsRequest();
        createCustomerSecurityQuestionsRequest.setSecurityQuestions(securityQuestion);
        ExceptionResponse exceptionResponse = new ExceptionResponse(CUS_SEC_QUES_CREATE_FIELD_ERROR, "Customer security question can't be Empty");
        String exceptionResponseMapper = objectMapper.writeValueAsString(exceptionResponse);
        String customerSecurityQuestion = objectMapper.writeValueAsString(createCustomerSecurityQuestionsRequest);
        this.mockMvc.perform(put("/customer-service/client-api/v1/customers/Dipak/securityQuestions")
                        .contentType(MediaType.APPLICATION_JSON).content(customerSecurityQuestion))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createSecurityQuestions_withInvalidSize_shouldThrowException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<SecurityQuestion> securityQuestions = new ArrayList<>();
        securityQuestions.add(new SecurityQuestion());
        CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest = new CreateCustomerSecurityQuestionsRequest();
        createCustomerSecurityQuestionsRequest.setSecurityQuestions(securityQuestions);
        ExceptionResponse exceptionResponse = new ExceptionResponse(CUS_SEC_QUES_CREATE_3_QUES_ERROR, "3 questions are allowed.");
        String exceptionResponseMapper = objectMapper.writeValueAsString(exceptionResponse);
        String customerSecurityQuestion = objectMapper.writeValueAsString(createCustomerSecurityQuestionsRequest);
        this.mockMvc.perform(put("/customer-service/client-api/v1/customers/Dipak/securityQuestions")
                        .contentType(MediaType.APPLICATION_JSON).content(customerSecurityQuestion))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createSecurityQuestions_withInvalidAnswer_shouldThrowException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest = new CreateCustomerSecurityQuestionsRequest();
        SecurityQuestion securityQuestion = new SecurityQuestion();
        securityQuestion.setSecurityQuestionAnswer("A");
        List<SecurityQuestion> securityQuestionList = List.of(securityQuestion, securityQuestion, securityQuestion);
        createCustomerSecurityQuestionsRequest.setSecurityQuestions(securityQuestionList);
        ExceptionResponse exceptionResponse = new ExceptionResponse(CUS_SEC_QUES_VALIDATE_ERROR, "Security question answer should contain min 3 characters");
        String exceptionResponseMapper = objectMapper.writeValueAsString(exceptionResponse);
        String customerSecurityQuestion = objectMapper.writeValueAsString(createCustomerSecurityQuestionsRequest);
        this.mockMvc.perform(put("/customer-service/client-api/v1/customers/Dipak/securityQuestions")
                        .contentType(MediaType.APPLICATION_JSON).content(customerSecurityQuestion))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createSecurityQuestions_withInvalidQuestionId_shouldThrowException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest = new CreateCustomerSecurityQuestionsRequest();
        SecurityQuestion securityQuestion = new SecurityQuestion().securityQuestionId(UUID.randomUUID().toString()).securityQuestionAnswer("abc");
        SecurityQuestion securityQuestion1 = new SecurityQuestion().securityQuestionId(UUID.randomUUID().toString()).securityQuestionAnswer("abc");
        SecurityQuestion securityQuestion2 = new SecurityQuestion().securityQuestionId(UUID.randomUUID().toString()).securityQuestionAnswer("abc");
        List<SecurityQuestion> securityQuestionList = List.of(securityQuestion, securityQuestion1, securityQuestion2);
        createCustomerSecurityQuestionsRequest.setSecurityQuestions(securityQuestionList);
        ExceptionResponse exceptionResponse = new ExceptionResponse(CUS_SEC_QUES_NOT_FOUND, "Customer security question not found..");
        String exceptionResponseMapper = objectMapper.writeValueAsString(exceptionResponse);
        String customerSecurityQuestion = objectMapper.writeValueAsString(createCustomerSecurityQuestionsRequest);
        this.mockMvc.perform(put("/customer-service/client-api/v1/customers/Dipak/securityQuestions")
                        .contentType(MediaType.APPLICATION_JSON).content(customerSecurityQuestion))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isNotFound());
    }

    @Test
    void createSecurityQuestions_withValidSecurityQuestions_shouldCreateCustomerSecurityQuestions() throws Exception {
        List<SecurityQuestions> securityQuestionsList = securityQuestionsRepo.findAll();
        CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest = new CreateCustomerSecurityQuestionsRequest();
        SecurityQuestion securityQuestionsDto = new SecurityQuestion().securityQuestionId(securityQuestionsList.get(0).getId().toString()).securityQuestionAnswer("abc");
        SecurityQuestion securityQuestionsDto1 = new SecurityQuestion().securityQuestionId(securityQuestionsList.get(1).getId().toString()).securityQuestionAnswer("xyz");
        SecurityQuestion securityQuestionsDto2 = new SecurityQuestion().securityQuestionId(securityQuestionsList.get(2).getId().toString()).securityQuestionAnswer("pqr");
        List<SecurityQuestion> securityQuestionList = List.of(securityQuestionsDto, securityQuestionsDto1, securityQuestionsDto2);

        createCustomerSecurityQuestionsRequest.setSecurityQuestions(securityQuestionList);

        String customerSecurityQuestion = new ObjectMapper().writeValueAsString(createCustomerSecurityQuestionsRequest);

        this.mockMvc.perform(put("/customer-service/client-api/v1/customers/Dipak/securityQuestions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerSecurityQuestion))
                .andExpect(status().isCreated());
    }
}
