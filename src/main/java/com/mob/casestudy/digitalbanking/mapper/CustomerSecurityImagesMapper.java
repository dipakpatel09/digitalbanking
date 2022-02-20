package com.mob.casestudy.digitalbanking.mapper;

import com.digitalbanking.openapi.model.GetCustomerSecurityImageResponse;
import com.mob.casestudy.digitalbanking.entity.CustomerSecurityImages;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerSecurityImagesMapper {

    @Mapping(source = "customerSecurityImages.securityImages.id", target = "securityImageId")
    @Mapping(source = "customerSecurityImages.securityImages.securityImageName", target = "securityImageName")
    @Mapping(source = "customerSecurityImages.securityImages.securityImageURL", target = "securityImageUrl")
    GetCustomerSecurityImageResponse toDto(CustomerSecurityImages customerSecurityImages);

    default String map(UUID id) {
        return id.toString();
    }
}
