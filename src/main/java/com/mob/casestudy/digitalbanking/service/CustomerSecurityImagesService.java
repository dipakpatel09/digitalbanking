package com.mob.casestudy.digitalbanking.service;

import com.mob.casestudy.digitalbanking.repository.CustomerSecurityImagesRepo;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityImages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerSecurityImagesService {

    @Autowired
    CustomerSecurityImagesRepo customerSecurityImagesRepo;

    public void deleteCustomerSecurityImages(CustomerSecurityImages customerSecurityImages) {
        if (customerSecurityImages != null) {
            customerSecurityImagesRepo.delete(customerSecurityImages);
        }
    }
}
