package com.rewards.dto;


import java.time.LocalDate;

public class RewardsRequest {

    private LocalDate startDate;


    private LocalDate endDate;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "RewardsRequest{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public RewardsRequest(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
