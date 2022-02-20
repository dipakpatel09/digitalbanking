package com.mob.casestudy.digitalbanking.mapper;

import com.digitalbanking.openapi.model.SecurityImage;
import com.mob.casestudy.digitalbanking.entity.SecurityImages;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface SecurityImagesMapper {

    @Mapping(source = "id", target = "securityImageId")
    @Mapping(source = "securityImageURL", target = "securityImageUrl")
    SecurityImage toDto(SecurityImages securityImages);

    default String map(UUID id) {
        return id.toString();
    }
}
