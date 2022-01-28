package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.dto.CustomerSecurityQuestionsDto;
import com.mob.casestudy.digitalbanking.dto.CustomerSecurityQuestionsDtoList;
import com.mob.casestudy.digitalbanking.repository.CustomerRepo;
import com.mob.casestudy.digitalbanking.repository.CustomerSecurityQuestionsRepo;
import com.mob.casestudy.digitalbanking.repository.SecurityQuestionsRepo;
import com.mob.casestudy.digitalbanking.embedded.CustomerSecQuestion;
import com.mob.casestudy.digitalbanking.entity.*;
import com.mob.casestudy.digitalbanking.exception.CustomerNotFoundException;
import com.mob.casestudy.digitalbanking.exception.CustomerQuestionNotFoundException;
import com.mob.casestudy.digitalbanking.exception.UserNotFoundException;
import com.mob.casestudy.digitalbanking.exception.ValidationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    @Autowired
    CustomerOTPService customerOTPService;

    @Autowired
    SecurityQuestionsRepo securityQuestionsRepo;

    @Autowired
    CustomerSecurityImagesService customerSecurityImagesService;

    @Autowired
    CustomerSecurityQuestionsService customerSecurityQuestionsService;

    @Autowired
    CustomerSecurityQuestionsRepo customerSecurityQuestionsRepo;

    @Transactional
    public void deleteCustomer(String userName) {

        //TODO: Add the transactional annotation at proper place and test
        //TODO: Remove the data from parent able alone(using cascade options)
        //TODO: Fix all sonar issues

        Customer customer = findCustomerByUserName(userName);

        //TODO: Rename the method with suitable name
        deleteChildRecordOfCustomer(customer);

        customerRepo.delete(customer);
    }

    private Customer findCustomerByUserName(String userName) {
        return customerRepo.findByUserName(userName).orElseThrow(() -> new UserNotFoundException("Invalid User.." + userName));
    }

    private void deleteChildRecordOfCustomer(Customer customer) {

        CustomerOTP customerOTP = customer.getCustomerOTP();
        CustomerSecurityImages customerSecurityImages = customer.getCustomerSecurityImages();
        List<CustomerSecurityQuestions> customerSecurityQuestions = customer.getCustomerSecurityQuestions();

        customerSecurityImagesService.deleteCustomerSecurityImages(customerSecurityImages);
        customerSecurityQuestionsService.deleteCustomerSecurityQuestions(customerSecurityQuestions);
        customerOTPService.deleteCustomerOTP(customerOTP);
    }

    //Create Security Questions for Customer

    public void createSecurityQuestions(String userName, CustomerSecurityQuestionsDtoList customerSecurityQuestionsDtoList) {

        Customer byUserName = validateCustomer(userName);

        if (customerSecurityQuestionsDtoList.getCustomerSecurityQuestionsDtos().size() < 3) {

            throw new ValidationFailedException("Atleast 3 questions should be provided.");
        }

        List<CustomerSecurityQuestions> customerSecurityQuestionsRepoAll = customerSecurityQuestionsRepo.findAll();

       // List<CustomerSecurityQuestionsDto> customerSecurityQuestionsDtos = customerSecurityQuestionsRepoAll.stream().map(CustomerSecurityQuestions::toDto).toList();


        List<CustomerSecurityQuestions> customerSecurityQuestions = customerSecurityQuestionsRepoAll
                .stream()
                .map(customerSecurityQuestionDto1 -> this.getEntity(customerSecurityQuestionDto1, customer, securityQuestionList)).toList();

        if (customerSecurityQuestionsDtoList == null || customerSecurityQuestionsDtoList.isEmpty()) {

            throw new ValidationFailedException("Validation failed...");

        }


        List<CustomerSecurityQuestionsDto> collect = customerSecurityQuestionsDtoList.stream().collect(Collectors.toList());

        if (customerSecurityQuestionsDtoList.get(0) == null || customerSecurityQuestionsDtoList.get(1) == null || customerSecurityQuestionsDtoList.get(2) == null) {
            throw new ValidationFailedException("Validation Failed.");
        }

        CustomerSecurityQuestions customerSecurityQuestions = new CustomerSecurityQuestions();
        customerSecurityQuestions.setCustomer(byUserName);

        SecurityQuestions securityQuestions = new SecurityQuestions();
        customerSecurityQuestions.setSecurityQuestions(securityQuestions);

        if (customerSecurityQuestionsDtoList.isEmpty()) {
            throw new ValidationFailedException("Customer security questions can't be Empty.");
        }

        SecurityQuestions securityQuestions1 = validateQuestionId(customerSecurityQuestionsDtoList.get(0).getSecurityQuestionId());

        CustomerSecQuestion customerSecurityQuestion = new CustomerSecQuestion(byUserName.getId(), securityQuestions1.getId());

        customerSecurityQuestions.setCustomerSecQuestion(customerSecurityQuestion);
        customerSecurityQuestions.setCreatedOn(LocalDateTime.now());
        customerSecurityQuestionsDtoList.forEach(customerSecurityQuestion1 -> securityQuestions.setSecurityQuestionText(customerSecurityQuestion1.getSecurityQuestionText()));
        customerSecurityQuestionsDtoList.forEach(customerSecurityQuestion1 -> customerSecurityQuestions.setSecurityQuestionAnswer(customerSecurityQuestion1.getSecurityQuestionAnswer()));

        securityQuestionsRepo.save(securityQuestions);
        customerSecurityQuestionsRepo.save(customerSecurityQuestions);

    }

    public Customer validateCustomer(String userName) {
        Optional<Customer> byUserName = customerRepo.findByUserName(userName);

        if (byUserName.isEmpty()) {
            throw new CustomerNotFoundException("The requested user not found.." + userName);
        }
        return byUserName.get();
    }

    public SecurityQuestions validateQuestionId(UUID id) {
        Optional<SecurityQuestions> byId = securityQuestionsRepo.findById(id);
        if (byId.isEmpty()) {
            throw new CustomerQuestionNotFoundException("Customer question not found..");
        }
        return byId.get();
    }

    private CustomerSecurityQuestions getEntity(CustomerSecurityQuestionsDto customerSecurityQuestionsDto, Customer customer, List<SecurityQuestions> securityQuestions) {

        CustomerSecurityQuestions customerSecurityQuestions = new CustomerSecurityQuestions(new CustomerSecQuestion(),customerSecurityQuestionsDto.getSecurityQuestionAnswer());

        SecurityQuestions securityQuestion = securityQuestions.stream().filter(securityQuestion1 -> securityQuestion1.getId().equals(customerSecurityQuestionsDto.getSecurityQuestionId())).findAny().orElseThrow(() -> new CustomerQuestionNotFoundException("Invalid Question.."));

        customerSecurityQuestions.setCustomer(customer);
        customerSecurityQuestions.setSecurityQuestions(securityQuestion);

        return customerSecurityQuestions;
    }
}
