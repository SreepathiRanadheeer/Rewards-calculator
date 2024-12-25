package com.rewards.controller;

import com.rewards.dto.RewardsRequest;
import com.rewards.dto.RewardsResponse;
import com.rewards.service.RewardsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rewards")
public class RewardsController {
    private final RewardsService rewardsService;
    private static final Logger LOGGER = LoggerFactory.getLogger(RewardsController.class);

    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }


    @GetMapping
    public ResponseEntity<List<RewardsResponse>> getRewards(@Validated RewardsRequest request) {
        LOGGER.info("Fetching rewards for all customers within the timeframe: {}", request);
        List<RewardsResponse> rewards = rewardsService.calculateRewards(request);
        return ResponseEntity.ok(rewards);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<RewardsResponse> getCustomerRewards(
            @PathVariable Long customerId,
            @Validated RewardsRequest request) {
        LOGGER.info("Fetching rewards for customerId: {} within the timeframe: {}", customerId, request);
        RewardsResponse rewards = rewardsService.calculateRewardsForCustomer(customerId, request);
        return ResponseEntity.ok(rewards);
    }
}
