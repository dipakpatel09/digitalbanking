package com.mob.casestudy.digitalbanking.integration;

import static com.mob.casestudy.digitalbanking.customerror.ErrorList.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.digitalbanking.openapi.model.ValidateOtpRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mob.casestudy.digitalbanking.DigitalbankingApplication;
import com.mob.casestudy.digitalbanking.embedded.CustomerOTPId;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DigitalbankingApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@TestPropertySource(value = "classpath:application.yml")
@AutoConfigureMockMvc
class CustomerOTPServiceTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    CustomerOTPRepo customerOTPRepo;

    @BeforeEach
    void setUp() {
        Customer customer = Customer.builder().userName("Dipak").firstName("Dipak").lastName("Patel").phoneNumber("0123456789").email("abc@gmail.com").status(Status.ACTIVE).preferredLanguage(Language.EN).externalId("dipak007").createdBy("Dipak1").createdOn(LocalDateTime.now()).updatedBy("Dipak2").updatedOn(LocalDateTime.now()).build();
        customerRepo.save(customer);
        Customer customer1 = Customer.builder().userName("Dipak447").firstName("Dipak").lastName("Patel").phoneNumber("0123456789").email("abc@gmail.com").status(Status.ACTIVE).preferredLanguage(Language.EN).externalId("dipak007").createdBy("Dipak1").createdOn(LocalDateTime.now()).updatedBy("Dipak2").updatedOn(LocalDateTime.now()).build();
        customerRepo.save(customer1);
        Customer customer2 = Customer.builder().userName("Dipak007").firstName("Dipak").lastName("Patel").phoneNumber("0123456789").email("abc@gmail.com").status(Status.ACTIVE).preferredLanguage(Language.EN).externalId("dipak007").createdBy("Dipak1").createdOn(LocalDateTime.now()).updatedBy("Dipak2").updatedOn(LocalDateTime.now()).build();
        customerRepo.save(customer2);

        CustomerOTP otp = CustomerOTP.builder().customerOTPId(new CustomerOTPId()).otpMessage("This is OTP").otp("123456").otpRetries(1).expiryOn(LocalDateTime.now().minusMinutes(1)).createdOn(LocalDateTime.now()).build();
        CustomerOTP otp1 = CustomerOTP.builder().customerOTPId(new CustomerOTPId()).otpMessage("This is OTP").otp("123456").otpRetries(5).expiryOn(LocalDateTime.now().plusMinutes(1)).createdOn(LocalDateTime.now()).build();
        CustomerOTP otp2 = CustomerOTP.builder().customerOTPId(new CustomerOTPId()).otpMessage("This is OTP").otp("123456").otpRetries(2).expiryOn(LocalDateTime.now().plusMinutes(1)).createdOn(LocalDateTime.now()).build();
        otp.setCustomer(customer);
        otp1.setCustomer(customer1);
        otp2.setCustomer(customer2);
        customerOTPRepo.save(otp);
        customerOTPRepo.save(otp1);
        customerOTPRepo.save(otp2);
    }

    @AfterEach
    void tearDown() {
        customerOTPRepo.deleteAll();
        customerRepo.deleteAll();
    }

    @Test
    void validateOTPForUser_withInvalidCustomer_shouldThrowException() throws Exception {
        String name = "abc";
        ObjectMapper objectMapper = new ObjectMapper();
        ValidateOtpRequest validateOtpRequest = new ValidateOtpRequest();
        validateOtpRequest.setUserName(name);
        validateOtpRequest.setOtp("678987");
        ExceptionResponse exceptionResponse = new ExceptionResponse(CUS_NOT_FOUND, name + " user not found");
        String exceptionResponseMapper = objectMapper.writeValueAsString(exceptionResponse);
        String otpRequestMapper = objectMapper.writeValueAsString(validateOtpRequest);
        this.mockMvc.perform(post("/customer-service/service-api/v2/otp/validate")
                        .contentType(APPLICATION_JSON).content(otpRequestMapper))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isNotFound());
    }

    @Test
    void validateOTPForUser_withInvalidOTPLength_shouldThrowException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ValidateOtpRequest validateOtpRequest = new ValidateOtpRequest();
        validateOtpRequest.setUserName("abc");
        validateOtpRequest.setOtp("");
        ExceptionResponse exceptionResponse = new ExceptionResponse(OTP_IS_NULL_OR_EMPTY, "OTP can not Empty");
        String exceptionResponseMapper = objectMapper.writeValueAsString(exceptionResponse);
        String otpRequestMapper = objectMapper.writeValueAsString(validateOtpRequest);
        this.mockMvc.perform(post("/customer-service/service-api/v2/otp/validate")
                        .contentType(APPLICATION_JSON).content(otpRequestMapper))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isBadRequest());
    }

    @Test
    void validateOTPForUser_IfOTPTimeIsExpired_shouldThrowException() throws Exception {
        String name = "Dipak";
        ObjectMapper objectMapper = new ObjectMapper();
        ValidateOtpRequest validateOtpRequest = new ValidateOtpRequest();
        validateOtpRequest.setUserName(name);
        Customer customer = customerRepo.findByUserName(name).get();
        String otp = customer.getCustomerOTP().getOtp();
        validateOtpRequest.setOtp(otp);
        ExceptionResponse exceptionResponse = new ExceptionResponse(CUS_INITIATE_OTP_EXPIRED, "Time expired");
        String exceptionResponseMapper = objectMapper.writeValueAsString(exceptionResponse);
        String otpRequestMapper = objectMapper.writeValueAsString(validateOtpRequest);
        this.mockMvc.perform(post("/customer-service/service-api/v2/otp/validate")
                        .contentType(APPLICATION_JSON).content(otpRequestMapper))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isBadRequest());
    }

    @Test
    void validateOTPForUser_IfRemainingTryExceeded_shouldThrowException() throws Exception {
        String name = "Dipak447";
        ObjectMapper objectMapper = new ObjectMapper();
        ValidateOtpRequest validateOtpRequest = new ValidateOtpRequest();
        validateOtpRequest.setUserName(name);
        validateOtpRequest.setOtp("876745");
        ExceptionResponse exceptionResponse = new ExceptionResponse(NO_FAILED_OTP_ATTEMPT_EXCEED, "No of Failed OTP Attempts Exceeded");
        String exceptionResponseMapper = objectMapper.writeValueAsString(exceptionResponse);
        String otpRequestMapper = objectMapper.writeValueAsString(validateOtpRequest);
        this.mockMvc.perform(post("/customer-service/service-api/v2/otp/validate")
                        .contentType(APPLICATION_JSON).content(otpRequestMapper))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isBadRequest());
    }

    @Test
    void validateOTPForUser_withInvalidOTP_shouldThrowException() throws Exception {
        String name = "Dipak007";
        ObjectMapper objectMapper = new ObjectMapper();
        ValidateOtpRequest validateOtpRequest = new ValidateOtpRequest();
        validateOtpRequest.setUserName(name);
        validateOtpRequest.setOtp("876547");
        ExceptionResponse exceptionResponse = new ExceptionResponse(INVALID_OTP, "Entered OTP is invalid");
        String exceptionResponseMapper = objectMapper.writeValueAsString(exceptionResponse);
        String otpRequestMapper = objectMapper.writeValueAsString(validateOtpRequest);
        this.mockMvc.perform(post("/customer-service/service-api/v2/otp/validate")
                        .contentType(APPLICATION_JSON).content(otpRequestMapper))
                .andExpect(content().json(exceptionResponseMapper))
                .andExpect(status().isBadRequest());
    }

    @Test
    void validateOTPForUser_withValidOTP_shouldValidateTheOTP() throws Exception {
        String name = "Dipak007";
        ObjectMapper objectMapper = new ObjectMapper();
        ValidateOtpRequest validateOtpRequest = new ValidateOtpRequest();
        validateOtpRequest.setUserName(name);
        Customer customer = customerRepo.findByUserName(name).get();
        String otp = customer.getCustomerOTP().getOtp();
        validateOtpRequest.setOtp(otp);
        String otpRequestMapper = objectMapper.writeValueAsString(validateOtpRequest);
        this.mockMvc.perform(post("/customer-service/service-api/v2/otp/validate")
                        .contentType(APPLICATION_JSON).content(otpRequestMapper))
                .andExpect(status().isOk());
    }
}
