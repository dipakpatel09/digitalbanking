package com.mob.casestudy.digitalbanking.repository;

import com.mob.casestudy.digitalbanking.entity.CustomerOTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerOTPRepo extends JpaRepository<CustomerOTP, UUID> {

}
