package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.repository.CustomerRepo;
import com.mob.casestudy.digitalbanking.entity.*;
import com.mob.casestudy.digitalbanking.exception.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepo customerRepo;

    @Mock
    CustomerOTPService customerOTPService;

    @Mock
    CustomerSecurityImagesService customerSecurityImagesService;

    @Mock
    CustomerSecurityQuestionsService customerSecurityQuestionsService;

    @Test
    void deleteCustomer_withNullCustomer_shouldThrowException() {

        String name = "Uzair";

        Mockito.when(customerRepo.findByUserName(name)).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(UserNotFoundException.class, () -> customerService.deleteCustomer(name));
    }

    @Test
    void deleteCustomer_withValidCustomer_shouldDeleteExistingCustomer(){

        String username="Naitik";

        Customer customer = new Customer();

        CustomerOTP customerOTP=new CustomerOTP();
        customer.setCustomerOTP(customerOTP);

        CustomerSecurityImages customerSecurityImages =new CustomerSecurityImages();
        customer.setCustomerSecurityImages(customerSecurityImages);

        CustomerSecurityQuestions customerSecurityQuestions = new CustomerSecurityQuestions();
        customer.addCustomerSecurityQuestions(customerSecurityQuestions);

        Mockito.when(customerRepo.findByUserName(username)).thenReturn(Optional.ofNullable(customer));

        customerService.deleteCustomer(username);

        Mockito.verify(customerSecurityImagesService).deleteCustomerSecurityImages(customerSecurityImages);
        Mockito.verify(customerSecurityQuestionsService).deleteCustomerSecurityQuestions(List.of(customerSecurityQuestions));
        Mockito.verify(customerOTPService).deleteCustomerOTP(customerOTP);
        Mockito.verify(customerRepo).delete(customer);

    }
}











