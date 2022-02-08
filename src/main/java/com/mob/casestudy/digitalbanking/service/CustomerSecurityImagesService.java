package com.mob.casestudy.digitalbanking.service;

import static com.mob.casestudy.digitalbanking.errorlist.CustomError.*;

import com.mob.casestudy.digitalbanking.dto.CustomerSecurityImagesDto;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityImages;
import com.mob.casestudy.digitalbanking.entity.SecurityImages;
import com.mob.casestudy.digitalbanking.exception.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerSecurityImagesService {

    @Autowired
    CustomerService customerService;

    public CustomerSecurityImagesDto getSecurityImageByUserName(String userName) {
        Customer byUserName = customerService.findCustomerByUserName(userName, CUS_SEC_IMG_CUS_NOT_FOUND, "User " + userName + " not found");
        CustomerSecurityImages customerSecurityImages = byUserName.getCustomerSecurityImages();
        checkCustomerSecurityImage(customerSecurityImages);
        SecurityImages securityImages = customerSecurityImages.getSecurityImages();
        return customerSecurityImages.toDto(securityImages);
    }

    private void checkCustomerSecurityImage(CustomerSecurityImages customerSecurityImages) {
        if (customerSecurityImages == null) {
            throw new CustomNotFoundException(CUS_SEC_IMG_NOT_FOUND, "Images not found");
        }
    }
}
