package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.repository.CustomerSecurityImagesRepo;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityImages;
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
    CustomerSecurityImagesRepo customerSecurityImagesRepo;

    @Test
    void deleteCustomerSecurityImages_withNull_shouldThrowNullPointerException() {

        customerSecurityImagesService.deleteCustomerSecurityImages(null);
        Mockito.verify(customerSecurityImagesRepo,Mockito.times(0)).delete(Mockito.any());

    }

    @Test
    void deleteCustomerSecurityImages_withValidList_shouldDeleteImages() {

        CustomerSecurityImages images=new CustomerSecurityImages();
        customerSecurityImagesService.deleteCustomerSecurityImages(images);
        Mockito.verify(customerSecurityImagesRepo).delete(images);

    }
}