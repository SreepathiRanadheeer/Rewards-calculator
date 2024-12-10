package com.rewards.service;

import com.rewards.model.RewardsResponse;
import com.rewards.model.Transaction;
import com.rewards.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RewardsService {
    private final TransactionRepository transactionRepository;

    public RewardsService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<RewardsResponse> calculateRewards() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusMonths(3).withDayOfMonth(1);
        LocalDate endDate = now.withDayOfMonth(1).minusDays(1);

        List<Transaction> transactions = transactionRepository.findByTransactionDateBetween(startDate, endDate);

        Map<String, Map<Month, Integer>> rewardsMap = new HashMap<>();

        for (Transaction transaction : transactions) {
            String customerName = transaction.getCustomer().getName();
            Month month = transaction.getTransactionDate().getMonth();
            int points = calculatePoints(transaction.getAmount());

            rewardsMap
                    .computeIfAbsent(customerName, k -> new HashMap<>())
                    .merge(month, points, Integer::sum);
        }

        return rewardsMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().entrySet().stream()
                        .map(e -> new RewardsResponse(entry.getKey(), e.getKey().getValue(), e.getValue())))
                .collect(Collectors.toList());
    }

    private int calculatePoints(double amount) {
        int points = 0;
        if (amount > 100) points += (amount - 100) * 2;
        if (amount > 50) points += Math.min(amount - 50, 50);
        return points;
    }
}

