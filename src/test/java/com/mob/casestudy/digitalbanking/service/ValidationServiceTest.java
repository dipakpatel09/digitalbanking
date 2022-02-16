package com.mob.casestudy.digitalbanking.service;

import com.digitalbanking.openapi.model.CreateCustomerSecurityQuestionsRequest;
import com.digitalbanking.openapi.model.SecurityQuestion;
import com.digitalbanking.openapi.model.ValidateOtpRequest;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerOTP;
import com.mob.casestudy.digitalbanking.entity.SecurityQuestions;
import com.mob.casestudy.digitalbanking.exception.CustomBadRequestException;
import com.mob.casestudy.digitalbanking.exception.CustomNotFoundException;
import com.mob.casestudy.digitalbanking.repository.CustomerOTPRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {

    @InjectMocks
    ValidationService validationService;

    @Mock
    CustomerOTPRepo customerOTPRepo;

    @Test
    void validateSecurityQuestion_withEmptySecurityQuestionList_shouldThrowException() {
        List<SecurityQuestion> securityQuestions = new ArrayList<>();
        CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest = new CreateCustomerSecurityQuestionsRequest();
        createCustomerSecurityQuestionsRequest.setSecurityQuestions(securityQuestions);
        Assertions.assertThrows(CustomBadRequestException.class, () -> validationService.validateSecurityQuestion(createCustomerSecurityQuestionsRequest));
    }

    @Test
    void validateSecurityQuestion_withValidSecurityQuestionList_shouldReturnSecurityQuestion() {
        List<SecurityQuestion> securityQuestions = new ArrayList<>();
        securityQuestions.add(new SecurityQuestion());
        CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest = new CreateCustomerSecurityQuestionsRequest();
        createCustomerSecurityQuestionsRequest.setSecurityQuestions(securityQuestions);
        validationService.validateSecurityQuestion(createCustomerSecurityQuestionsRequest);
    }

    @Test
    void validateSecurityQuestionSize_withInvalidSize_shouldThrowException() {
        List<SecurityQuestion> securityQuestions = new ArrayList<>();
        securityQuestions.add(new SecurityQuestion());
        CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest = new CreateCustomerSecurityQuestionsRequest();
        createCustomerSecurityQuestionsRequest.setSecurityQuestions(securityQuestions);
        Assertions.assertThrows(CustomBadRequestException.class, () -> validationService.validateSecurityQuestionSize(createCustomerSecurityQuestionsRequest));
    }

    @Test
    void validateSecurityQuestionSize_withValidSize_shouldReturnSecurityQuestion() {
        List<SecurityQuestion> securityQuestions = List.of(new SecurityQuestion(), new SecurityQuestion(), new SecurityQuestion());
        CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest = new CreateCustomerSecurityQuestionsRequest();
        createCustomerSecurityQuestionsRequest.setSecurityQuestions(securityQuestions);
        validationService.validateSecurityQuestionSize(createCustomerSecurityQuestionsRequest);
    }

    @Test
    void validateSecurityQuestionAnswer_withInvalidAnswer_shouldThrowException() {
        CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest = new CreateCustomerSecurityQuestionsRequest();
        SecurityQuestion securityQuestion = new SecurityQuestion();
        securityQuestion.setSecurityQuestionAnswer("A");
        List<SecurityQuestion> securityQuestionList = List.of(securityQuestion, securityQuestion, securityQuestion);
        createCustomerSecurityQuestionsRequest.setSecurityQuestions(securityQuestionList);
        Assertions.assertThrows(CustomBadRequestException.class, () -> validationService.validateSecurityQuestionAnswer(createCustomerSecurityQuestionsRequest));
    }

    @Test
    void validateSecurityQuestionAnswer_withValidAnswer_shouldReturnSecurityQuestionAnswer() {
        CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest = new CreateCustomerSecurityQuestionsRequest();
        SecurityQuestion securityQuestion = new SecurityQuestion();
        securityQuestion.setSecurityQuestionAnswer("Mumbai");
        List<SecurityQuestion> securityQuestionList = List.of(securityQuestion, securityQuestion, securityQuestion);
        createCustomerSecurityQuestionsRequest.setSecurityQuestions(securityQuestionList);
        Assertions.assertDoesNotThrow(() -> validationService.validateSecurityQuestionAnswer(createCustomerSecurityQuestionsRequest));
    }

    @Test
    void validateQuestionId_withInvalidQuestionId_shouldThrowException() {
        UUID id = UUID.randomUUID();
        SecurityQuestions securityQuestions = new SecurityQuestions();
        securityQuestions.setId(UUID.randomUUID());
        List<SecurityQuestions> securityQuestionsList = new ArrayList<>();
        securityQuestionsList.add(securityQuestions);
        Assertions.assertThrows(CustomNotFoundException.class, () -> validationService.validateQuestionId(id, securityQuestionsList));
    }

    @Test
    void validateQuestionId_withValidQuestionId_shouldReturnSecurityQuestion() {
        UUID id = UUID.randomUUID();
        SecurityQuestions securityQuestions = new SecurityQuestions();
        securityQuestions.setId(id);
        List<SecurityQuestions> securityQuestionsList = new ArrayList<>();
        securityQuestionsList.add(securityQuestions);
        SecurityQuestions actual = validationService.validateQuestionId(id, securityQuestionsList);
        Assertions.assertEquals(securityQuestions, actual);
    }

    @Test
    void validateOTPForUser_withInvalidOTPLength_shouldThrowExceptionA() {
        ValidateOtpRequest validateOtpRequest = new ValidateOtpRequest();
        validateOtpRequest.setOtp("");
        Assertions.assertThrows(CustomBadRequestException.class, () -> validationService.validateOTP(validateOtpRequest));
    }

    @Test
    void validateOTPForUser_IfOTPTimeIsExpired_shouldThrowException() {
        Customer customer = new Customer();
        CustomerOTP customerOTP = new CustomerOTP();
        customerOTP.setExpiryOn(LocalDateTime.now().minusMinutes(10));
        customer.setCustomerOTP(customerOTP);
        Assertions.assertThrows(CustomBadRequestException.class, () -> validationService.validateOTPExpiryTime(customerOTP));
    }

    @Test
    void validateOTPForUser_IfRemainingTryExceeded_shouldThrowExceptionC() {
        ValidateOtpRequest validateOtpRequest = new ValidateOtpRequest();
        Customer customer = new Customer();
        CustomerOTP customerOTP = new CustomerOTP();
        customerOTP.setOtpRetries(7);
        customer.setCustomerOTP(customerOTP);
        Assertions.assertThrows(CustomBadRequestException.class, () -> validationService.validateOTPAttempt(customerOTP, validateOtpRequest));
    }

    @Test
    void validateOTPForUser_withInvalidOTP_shouldThrowExceptionCA() {
        ValidateOtpRequest validateOtpRequest = new ValidateOtpRequest();
        validateOtpRequest.setOtp("123789");
        Customer customer = new Customer();
        CustomerOTP customerOTP = new CustomerOTP();
        customerOTP.setOtp("123456");
        customerOTP.setOtpRetries(2);
        customer.setCustomerOTP(customerOTP);
        Mockito.when(customerOTPRepo.save(customerOTP)).thenReturn(customerOTP);
        Assertions.assertThrows(CustomBadRequestException.class, () -> validationService.validateOTPAttempt(customerOTP, validateOtpRequest));
    }
}