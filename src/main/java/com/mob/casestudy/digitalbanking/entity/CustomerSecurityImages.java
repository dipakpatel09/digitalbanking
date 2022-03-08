package com.mob.casestudy.digitalbanking.entity;

import com.mob.casestudy.digitalbanking.embedded.CustomerSecImage;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerSecurityImages {

    @EmbeddedId
    private CustomerSecImage customerSecImage = new CustomerSecImage();

    @Column(length = 50)
    private String securityImageCaption;

    @Column(length = 50)
    private LocalDateTime createdOn;

    @OneToOne
    @MapsId("customerId")
    private Customer customer;

    @ManyToOne
    @MapsId("securityImageId")
    private SecurityImages securityImages;
}
