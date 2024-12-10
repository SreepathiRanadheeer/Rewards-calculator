package com.rewards.repository;

import com.rewards.model.Customer;
import com.rewards.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
