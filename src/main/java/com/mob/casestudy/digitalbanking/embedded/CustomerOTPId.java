package com.mob.casestudy.digitalbanking.embedded;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class CustomerOTPId implements Serializable {

    private UUID customerId;

    private UUID otpId;

    public CustomerOTPId() {
        otpId = UUID.randomUUID();
    }

    public CustomerOTPId(UUID customerId, UUID otpId) {
        this.customerId = customerId;
        this.otpId = otpId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getOtpId() {
        return otpId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerOTPId that = (CustomerOTPId) o;
        return customerId.equals(that.customerId) && otpId.equals(that.otpId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, otpId);
    }

    @Override
    public String toString() {
        return "CustomerOTPId{" +
                "customerId=" + customerId +
                ", otpId=" + otpId +
                '}';
    }
}
