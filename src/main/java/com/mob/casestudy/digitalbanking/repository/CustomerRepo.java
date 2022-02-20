package com.mob.casestudy.digitalbanking.repository;

import com.mob.casestudy.digitalbanking.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByUserName(String userName);
    boolean existsByUserName(String userName);
}
