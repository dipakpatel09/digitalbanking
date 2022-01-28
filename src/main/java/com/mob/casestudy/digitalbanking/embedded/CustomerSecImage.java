package com.mob.casestudy.digitalbanking.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class CustomerSecImage implements Serializable {

    @Column(length = 36)
    private UUID customerId;

    @Column(length = 36)
    private UUID securityImageId;

    public CustomerSecImage() {
    }

    public CustomerSecImage(UUID customerId, UUID securityImageId) {
        this.customerId = customerId;
        this.securityImageId = securityImageId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getSecurityImageId() {
        return securityImageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerSecImage that = (CustomerSecImage) o;
        return customerId.equals(that.customerId) && securityImageId.equals(that.securityImageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, securityImageId);
    }
}
