# Rewards Program API

## Overview
This is a Spring Boot application that calculates customer rewards based on their transactions. Customers earn points based on their spending:
- 1 point for every dollar spent between $50 and $100.
- 2 points for every dollar spent over $100 in a single transaction.

This project provides a RESTful API to dynamically calculate rewards over a given time frame.
Endpoints:
1.GET /rewards
        Fetch rewards for all customers.
      Parameters:
        startDate: YYYY-MM-DD (default: three months ago).
        endDate: YYYY-MM-DD (default: today).

Request:
    {
        "startDate": "2024-09-01",
        "endDate": "2024-12-31"
    }
Response:
[
{
    "customerName":"Rana",
    "month": "OCTOBER",
    "points": 120,
    "transactions": [
    {
        "date": "2024-10-10",   
        "amount": 120.0
    },
    {   
    "date": "2024-10-20",
    "amount": 75.0
    }
    ]
},
{
"customerName": "Sree",
"month": "NOVEMBER",
"points": 90,
"transactions": [
    {
    "date": "2024-11-15",
    "amount": 95.0
    }
    ]
}
]

2.GET /rewards/{customerId}
          Fetch rewards for a specific customer.
    Request:{
    "startDate": "2024-09-01",
    "endDate": "2024-12-31"
    }
    Response:
    {
    "customerName": "Sree",
    "totalPoints": 210,
    "monthlyRewards": [
    {
    "month": "OCTOBER",
    "points": 120,
    "transactions": [
    {
    "date": "2024-10-10",
    "amount": 120.0
    },
    {
    "date": "2024-10-20",
    "amount": 75.0
            }
    ]
    },
    {
    "month": "NOVEMBER",    
    "points": 90,
    "transactions": [
    {
    "date": "2024-11-15",
    "amount": 95.0
    }
    ]
}
]
}


    
---


## Features
- Calculate rewards for each customer by month and in total.
- Dynamically configurable time frames for reward calculation.
- Detailed breakdown of transactions and points.
- RESTful endpoint for integration.
- Input validation to ensure proper requests.
- Exception handling with meaningful error responses.

---

## Technologies Used
- **Programming Language**: Java 17
- **Framework**: Spring Boot
- **Build Tool**: Maven
- **Database**: Mysql
- **Testing**: JUnit, Mockito


