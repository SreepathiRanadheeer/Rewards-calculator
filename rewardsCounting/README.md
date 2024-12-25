# Rewards Program API

## Overview
This is a Spring Boot application that calculates customer rewards based on their transactions. Customers earn points based on their spending:
- 1 point for every dollar spent between $50 and $100.
- 2 points for every dollar spent over $100 in a single transaction.

This project provides a RESTful API to dynamically calculate rewards over a given time frame.
Endpoints:
      GET /rewards
        Fetch rewards for all customers.
      Parameters:
        startDate: YYYY-MM-DD (default: three months ago).
        endDate: YYYY-MM-DD (default: today).
      GET /rewards/{customerId}
          Fetch rewards for a specific customer.

---

## Features
- Calculate rewards for each customer by month and in total.
- Dynamically configurable time frames for reward calculation.
- Detailed breakdown of transactions and points.
- RESTful endpoints for integration.
- Input validation to ensure proper requests.
- Exception handling with meaningful error responses.

---

## Technologies Used
- **Programming Language**: Java 17
- **Framework**: Spring Boot
- **Build Tool**: Maven
- **Database**: Mysql
- **Testing**: JUnit, Mockito


