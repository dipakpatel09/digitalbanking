package com.mob.casestudy.digitalbanking.service;

import static com.mob.casestudy.digitalbanking.customerror.ErrorList.*;

import com.digitalbanking.openapi.model.CreateCustomerSecurityQuestionsRequest;
import com.digitalbanking.openapi.model.ValidateOtpRequest;
import com.mob.casestudy.digitalbanking.entity.CustomerOTP;
import com.mob.casestudy.digitalbanking.entity.SecurityQuestions;
import com.mob.casestudy.digitalbanking.exception.CustomBadRequestException;
import com.mob.casestudy.digitalbanking.exception.CustomNotFoundException;
import com.mob.casestudy.digitalbanking.repository.CustomerOTPRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ValidationService {


    @Autowired
    CustomerOTPRepo customerOTPRepo;

    public void validateSecurityQuestion(CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest) {
        if (createCustomerSecurityQuestionsRequest.getSecurityQuestions().isEmpty()) {
            throw new CustomBadRequestException(CUS_SEC_QUES_CREATE_FIELD_ERROR, "Customer security question can't be Empty");
        }
    }

    public void validateSecurityQuestionSize(CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest) {
        if (createCustomerSecurityQuestionsRequest.getSecurityQuestions().size() != 3) {
            throw new CustomBadRequestException(CUS_SEC_QUES_CREATE_3_QUES_ERROR, "3 questions are allowed.");
        }
    }

    public void validateSecurityQuestionAnswer(CreateCustomerSecurityQuestionsRequest createCustomerSecurityQuestionsRequest) {
        for (int i = 0; i < 3; i++) {
            String ansLength = createCustomerSecurityQuestionsRequest.getSecurityQuestions().get(i).getSecurityQuestionAnswer().trim();
            if (ansLength.length() < 3) {
                throw new CustomBadRequestException(CUS_SEC_QUES_VALIDATE_ERROR, "Security question answer should contain min 3 characters");
            }
        }
    }

    public SecurityQuestions validateQuestionId(UUID id, List<SecurityQuestions> list) {
        for (SecurityQuestions securityQuestions : list) {
            if (securityQuestions.getId().equals(id)) {
                return securityQuestions;
            }
        }
        throw new CustomNotFoundException(CUS_SEC_QUES_NOT_FOUND, "Customer security question not found..");
    }

    public void validateOTP(ValidateOtpRequest validateOtpRequest) {
        if (validateOtpRequest.getOtp().isEmpty()) {
            throw new CustomBadRequestException(OTP_IS_NULL_OR_EMPTY, "OTP can not Empty");
        }
    }

    public void validateOTPExpiryTime(CustomerOTP customerOTP) {
        LocalDateTime expiryOn = customerOTP.getExpiryOn();
        if (LocalDateTime.now().isAfter(expiryOn)) {
            throw new CustomBadRequestException(CUS_INITIATE_OTP_EXPIRED, "Time expired");
        }
    }

    public void validateOTPAttempt(CustomerOTP customerOTP, ValidateOtpRequest validateOtpRequest) {
        Integer otpRetries = customerOTP.getOtpRetries();
        if (otpRetries == null) otpRetries = 0;
        if (otpRetries < 3) {
            checkInvalidOTP(customerOTP, validateOtpRequest);
        } else {
            throw new CustomBadRequestException(NO_FAILED_OTP_ATTEMPT_EXCEED, "No of Failed OTP Attempts Exceeded");
        }
    }

    private void checkInvalidOTP(CustomerOTP customerOTP, ValidateOtpRequest validateOtpRequest) {
        Integer otpRetries = customerOTP.getOtpRetries();
        if (!validateOtpRequest.getOtp().equals(customerOTP.getOtp())) {
            customerOTP.setOtpRetries(otpRetries + 1);
            customerOTPRepo.save(customerOTP);
            throw new CustomBadRequestException(INVALID_OTP, "Entered OTP is invalid");
        }
    }
}
