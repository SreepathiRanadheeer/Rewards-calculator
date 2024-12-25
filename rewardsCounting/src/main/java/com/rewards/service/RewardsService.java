package com.rewards.service;

import com.rewards.dto.RewardsRequest;
import com.rewards.dto.RewardsResponse;
import com.rewards.model.Customer;
import com.rewards.model.Transaction;
import com.rewards.repository.CustomerRepository;
import com.rewards.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RewardsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RewardsService.class);

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;

    public RewardsService(TransactionRepository transactionRepository, CustomerRepository customerRepository) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
    }

    /**
     * Calculate rewards for all customers within a specified time frame.
     * @param request RewardsRequest with start and end date.
     * @return List of RewardsResponse for all customers.
     */
    public List<RewardsResponse> calculateRewards(RewardsRequest request) {
        LOGGER.info("Calculating rewards for all customers from {} to {}", request.getStartDate(), request.getEndDate());
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after the end date.");
        }

        List<Transaction> transactions = transactionRepository.findByTransactionDateBetween(startDate, endDate);

        Map<Customer, List<Transaction>> customerTransactionMap = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCustomer));

        return customerTransactionMap.entrySet().stream()
                .flatMap(entry -> calculateRewardsPerCustomer(entry.getKey(), entry.getValue()).stream())
                .collect(Collectors.toList());
    }

    /**
     * Calculate rewards for a specific customer within a given time frame.
     * @param customerId Customer ID.
     * @param request RewardsRequest with start and end date.
     * @return RewardsResponse containing detailed customer rewards.
     */
    public RewardsResponse calculateRewardsForCustomer(Long customerId, RewardsRequest request) {
        LOGGER.info("Calculating rewards for customer ID: {} from {} to {}", customerId, request.getStartDate(), request.getEndDate());
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));

        List<Transaction> transactions = transactionRepository.findByCustomerIdAndTransactionDateBetween(
                customerId, request.getStartDate(), request.getEndDate());

        return calculateRewardsForSingleCustomer(customer, transactions);
    }

    /**
     * Calculate monthly rewards for a specific customer and a list of transactions.
     * @param customer Customer object.
     * @param transactions List of transactions.
     * @return RewardsResponse for each month.
     */
    private List<RewardsResponse> calculateRewardsPerCustomer(Customer customer, List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getTransactionDate().getMonth()))
                .entrySet().stream()
                .map(entry -> buildRewardsResponse(customer, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Builds a single RewardsResponse for a given customer and transactions in a specific month.
     * @param customer Customer object.
     * @param month Month of the transactions.
     * @param transactions List of transactions for the month.
     * @return RewardsResponse for the month.
     */
    private RewardsResponse buildRewardsResponse(Customer customer, Month month, List<Transaction> transactions) {
        int points = transactions.stream()
                .mapToInt(transaction -> calculatePoints(transaction.getAmount()))
                .sum();

        List<Transaction> transactionDetails = transactions.stream()
                .map(transaction -> new Transaction(
                        transaction.getTransactionDate(), transaction.getAmount()))
                .collect(Collectors.toList());

        return new RewardsResponse(customer.getName(), month, points, transactionDetails);
    }

    /**
     * Calculate rewards for all transactions of a single customer.
     * @param customer Customer object.
     * @param transactions List of transactions.
     * @return RewardsResponse containing details for the customer.
     */
    private RewardsResponse calculateRewardsForSingleCustomer(Customer customer, List<Transaction> transactions) {
        Map<Month, List<Transaction>> groupedByMonth = transactions.stream()
                .collect(Collectors.groupingBy(transaction -> transaction.getTransactionDate().getMonth()));

        List<RewardsResponse> monthlyRewards = groupedByMonth.entrySet().stream()
                .map(entry -> buildRewardsResponse(customer, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        int totalPoints = monthlyRewards.stream().mapToInt(RewardsResponse::getPoints).sum();

        return new RewardsResponse(customer.getName(), totalPoints, transactions);
    }

    /**
     * Calculate reward points for a single transaction amount.
     * @param amount Amount spent in the transaction.
     * @return Points earned for the transaction.
     */
    private int calculatePoints(double amount) {
        int points = 0;
        if (amount > 100) {
            points += (amount - 100) * 2;
            amount = 100;
        }
        if (amount > 50) {
            points += (amount - 50);
        }
        return points;
    }
}
