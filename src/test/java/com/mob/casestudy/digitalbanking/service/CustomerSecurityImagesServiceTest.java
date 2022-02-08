package com.mob.casestudy.digitalbanking.service;

import static com.mob.casestudy.digitalbanking.errorlist.CustomError.*;

import com.mob.casestudy.digitalbanking.dto.CustomerSecurityImagesDto;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityImages;
import com.mob.casestudy.digitalbanking.entity.SecurityImages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerSecurityImagesServiceTest {

    @InjectMocks
    CustomerSecurityImagesService customerSecurityImagesService;

    @Mock
    CustomerService customerService;

    @Test
    void getSecurityImageByUserName_withValidCustomer_shouldReturnCustomerSecurityImage() {
        String name = "Dipak";
        SecurityImages securityImages = new SecurityImages();
        Customer customer = new Customer();
        CustomerSecurityImages customerSecurityImages = new CustomerSecurityImages();
        customerSecurityImages.setSecurityImages(securityImages);
        customerSecurityImages.setSecurityImageCaption("Apple");
        customer.setCustomerSecurityImages(customerSecurityImages);
        CustomerSecurityImagesDto expected = customerSecurityImages.toDto(securityImages);
        Mockito.when(customerService.findCustomerByUserName(name, CUS_SEC_IMG_CUS_NOT_FOUND, "User " + name + " not found")).thenReturn(customer);
        CustomerSecurityImagesDto actual = customerSecurityImagesService.getSecurityImageByUserName(name);
        org.assertj.core.api.Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}