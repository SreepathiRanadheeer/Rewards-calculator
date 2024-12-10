package com.rewards.controller;

import com.rewards.model.RewardsResponse;
import com.rewards.service.RewardsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RewardsController {
    private final RewardsService rewardsService;

    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @GetMapping("/rewards")
    public List<RewardsResponse> getRewards() {
        return rewardsService.calculateRewards();
    }
}
