package com.mob.casestudy.digitalbanking.service;

import static com.mob.casestudy.digitalbanking.customerror.ErrorList.*;

import com.digitalbanking.openapi.model.GetCustomerSecurityImageResponse;
import com.mob.casestudy.digitalbanking.entity.Customer;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityImages;
import com.mob.casestudy.digitalbanking.exception.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerSecurityImagesService {

    @Autowired
    CustomerService customerService;

    public ResponseEntity<GetCustomerSecurityImageResponse> retrieveSecurityImageByUserName(String userName) {
        Customer byUserName = customerService.findCustomerByUserName(userName, CUS_SEC_IMG_CUS_NOT_FOUND, "User " + userName + " not found");
        CustomerSecurityImages customerSecurityImages = byUserName.getCustomerSecurityImages();
        checkCustomerSecurityImage(customerSecurityImages);
        return ResponseEntity.ok().body(toDto(customerSecurityImages));
    }

    private void checkCustomerSecurityImage(CustomerSecurityImages customerSecurityImages) {
        if (customerSecurityImages == null) {
            throw new CustomNotFoundException(CUS_SEC_IMG_NOT_FOUND, "Images not found");
        }
    }

    public GetCustomerSecurityImageResponse toDto(CustomerSecurityImages customerSecurityImages) {
        return new GetCustomerSecurityImageResponse().securityImageId(customerSecurityImages.getSecurityImages().getId().toString())
                .securityImageName(customerSecurityImages.getSecurityImages().getSecurityImageName())
                .securityImageCaption(customerSecurityImages.getSecurityImageCaption())
                .securityImageUrl(customerSecurityImages.getSecurityImages().getSecurityImageURL());
    }
}
