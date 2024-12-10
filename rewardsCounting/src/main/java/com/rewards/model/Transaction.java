package com.rewards.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate transactionDate;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Transaction(Long id, LocalDate transactionDate, double amount, Customer customer) {
        this.id = id;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.customer = customer;
    }

}
