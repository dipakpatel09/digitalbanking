package com.mob.casestudy.digitalbanking.mapper;

import com.digitalbanking.openapi.model.SecurityQuestion;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityQuestions;
import com.mob.casestudy.digitalbanking.entity.SecurityQuestions;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface CustomerSecurityQuestionsMapper {

    @Mapping(source = "securityQuestion.securityQuestionAnswer", target = "securityQuestionAnswer")
    @Mapping(source = "customer", target = "customer")
    @Mapping(source = "securityQuestions", target = "securityQuestions")
    @Mapping(source = "localDateTime", target = "createdOn")
    CustomerSecurityQuestions fromDto(SecurityQuestion securityQuestion, Customer customer, SecurityQuestions securityQuestions, LocalDateTime localDateTime);

}
