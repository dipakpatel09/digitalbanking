package com.mob.casestudy.digitalbanking.embedded;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class CustomerSecQuestion implements Serializable {

    private UUID customerId;
    private UUID securityQuestionId;

    public CustomerSecQuestion() {
    }

    public CustomerSecQuestion(UUID customerId, UUID securityQuestionId) {
        this.customerId = customerId;
        this.securityQuestionId = securityQuestionId;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public UUID getSecurityQuestionId() {
        return securityQuestionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerSecQuestion that = (CustomerSecQuestion) o;
        return customerId.equals(that.customerId) && securityQuestionId.equals(that.securityQuestionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, securityQuestionId);
    }
}
