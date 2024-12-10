package com.rewards.model;

import lombok.Data;

@Data
public class RewardsResponse {
    private String customerName;
    private int month;
    private int points;

    public RewardsResponse(String key, int value, Integer value1) {
    }
}