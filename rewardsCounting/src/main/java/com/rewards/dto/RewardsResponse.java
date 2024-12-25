package com.rewards.dto;

import com.rewards.model.Transaction;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public class RewardsResponse {
    private String customerName;
    private Month month;
    private int points;
    private List<Transaction> transactions;



    public RewardsResponse(String customerName, int totalPoints, List<Transaction> transactions) {
        this.customerName = customerName;
        this.month = null; // Optional for total-only responses
        this.points = totalPoints;
        this.transactions = transactions;
    }

    public RewardsResponse(String customerName, Month month, int points, List<Transaction> transactions) {
        this.customerName = customerName;
        this.month = month;
        this.points = points;
        this.transactions = transactions;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
