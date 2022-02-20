package com.mob.casestudy.digitalbanking.service;

import static com.mob.casestudy.digitalbanking.customerror.ErrorList.*;

import com.digitalbanking.openapi.model.GetCustomerSecurityImageResponse;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityImages;
import com.mob.casestudy.digitalbanking.entity.SecurityImages;
import com.mob.casestudy.digitalbanking.exception.CustomNotFoundException;
import com.mob.casestudy.digitalbanking.mapper.CustomerSecurityImagesMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CustomerSecurityImagesServiceTest {

    @InjectMocks
    CustomerSecurityImagesService customerSecurityImagesService;

    @Mock
    CustomerService customerService;

    @Mock
    CustomerSecurityImagesMapperImpl customerSecurityImagesMapper;

    @Test
    void getSecurityImageByUserName_withValidCustomer_shouldReturnCustomerSecurityImage() {
        String name = "Dipak";
        SecurityImages securityImages = new SecurityImages();
        securityImages.setId(UUID.randomUUID());
        Customer customer = new Customer();
        CustomerSecurityImages customerSecurityImages = new CustomerSecurityImages();
        customerSecurityImages.setSecurityImages(securityImages);
        customerSecurityImages.setSecurityImageCaption("Apple");
        customer.setCustomerSecurityImages(customerSecurityImages);

        GetCustomerSecurityImageResponse customerSecurityImageResponse = customerSecurityImagesMapper.toDto(customerSecurityImages);
        ResponseEntity<GetCustomerSecurityImageResponse> expected = ResponseEntity.ok().body(customerSecurityImageResponse);

        Mockito.when(customerService.findCustomerByUserName(name, CUS_SEC_IMG_CUS_NOT_FOUND, "User " + name + " not found")).thenReturn(customer);

        ResponseEntity<GetCustomerSecurityImageResponse> actual = customerSecurityImagesService.retrieveSecurityImageByUserName(name);
        org.assertj.core.api.Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void getSecurityImageByUserName__withNullCustomerSecurityImage_shouldThrowException() {
        String name = "Dipak";
        Customer customer = new Customer();
        customer.setCustomerSecurityImages(null);
        Mockito.when(customerService.findCustomerByUserName(name, CUS_SEC_IMG_CUS_NOT_FOUND, "User " + name + " not found")).thenReturn(customer);
        Assertions.assertThrows(CustomNotFoundException.class, () -> customerSecurityImagesService.retrieveSecurityImageByUserName(name));
    }
}