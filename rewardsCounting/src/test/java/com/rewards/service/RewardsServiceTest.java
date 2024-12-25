package com.rewards.service;

import com.rewards.dto.RewardsRequest;
import com.rewards.model.Customer;
import com.rewards.model.Transaction;
import com.rewards.repository.CustomerRepository;
import com.rewards.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RewardsServiceTest {

    private RewardsService rewardsService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RewardsRequest rewardsRequest;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        rewardsService = new RewardsService(transactionRepository,customerRepository);
    }

    @Test
    public void testCalculateRewards() {
        Customer raju = new Customer();
        raju.setId(1L);
        raju.setName("Raju");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(1L, LocalDate.of(2024, 10, 1), 120, raju),
                new Transaction(2L, LocalDate.of(2024, 11, 5), 90, raju)
        );

        when(transactionRepository.findByTransactionDateBetween(
                LocalDate.of(2024, 9, 1),
                LocalDate.of(2024, 11, 30)
        )).thenReturn(transactions);

        var results = rewardsService.calculateRewards(rewardsRequest);

        assertEquals(2, results.size());
        assertEquals(90, results.get(0).getPoints()); // 120 -> 90 points
        assertEquals(40, results.get(1).getPoints()); // 90 -> 40 points
    }
}