package com.rewards.controller;

import com.rewards.dto.RewardsRequest;
import com.rewards.dto.RewardsResponse;
import com.rewards.model.Transaction;
import com.rewards.service.RewardsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

class RewardsControllerTest {

    @InjectMocks
    private RewardsController rewardsController;

    @Mock
    private RewardsService rewardsService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup(rewardsController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetRewardsForAllCustomers() throws Exception {
        RewardsRequest rewardsRequest = new RewardsRequest(LocalDate.now().minusMonths(3), LocalDate.now());
        List<RewardsResponse> rewardsResponseList = getSampleRewardsResponse();

        // When the RewardsService is called, return predefined sample response
        when(rewardsService.calculateRewards(rewardsRequest)).thenReturn(rewardsResponseList);

        // Perform GET request to /rewards and assert the correct response
        mockMvc.perform(get("/rewards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rewardsRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerName").value("Rana"))
                .andExpect(jsonPath("$[0].points").value(120));

        verify(rewardsService, times(1)).calculateRewards(rewardsRequest);
    }

    @Test
    void testGetCustomerRewards() throws Exception {
        Long customerId = 1L;
        RewardsRequest rewardsRequest = new RewardsRequest(LocalDate.now().minusMonths(3), LocalDate.now());
        RewardsResponse rewardsResponse = getSampleRewardsResponseForCustomer();

        // When the RewardsService is called, return predefined sample response for specific customer
        when(rewardsService.calculateRewardsForCustomer(customerId, rewardsRequest)).thenReturn(rewardsResponse);

        // Perform GET request to /rewards/{customerId} and assert the correct response
        mockMvc.perform(get("/rewards/{customerId}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rewardsRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Rana"))
                .andExpect(jsonPath("$.points").value(120));

        verify(rewardsService, times(1)).calculateRewardsForCustomer(customerId, rewardsRequest);
    }

    @Test
    void testInvalidDateRange() throws Exception {
        RewardsRequest rewardsRequest = new RewardsRequest(LocalDate.now(), LocalDate.now().minusMonths(1));

        // Perform GET request and expect a BAD_REQUEST due to invalid date range
        mockMvc.perform(get("/rewards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rewardsRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Start date cannot be after the end date."));
    }

    @Test
    void testCustomerNotFound() throws Exception {
        Long customerId = 999L;
        RewardsRequest rewardsRequest = new RewardsRequest(LocalDate.now().minusMonths(3), LocalDate.now());

        // Simulate a scenario where customer doesn't exist
        when(rewardsService.calculateRewardsForCustomer(customerId, rewardsRequest))
                .thenThrow(new NoSuchElementException("Customer not found"));

        // Perform GET request and expect a NOT_FOUND response
        mockMvc.perform(get("/rewards/{customerId}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rewardsRequest)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Customer not found"));

        verify(rewardsService, times(1)).calculateRewardsForCustomer(customerId, rewardsRequest);
    }

    private List<RewardsResponse> getSampleRewardsResponse() {
        // A helper method to return a sample list of RewardsResponse
        RewardsResponse response = new RewardsResponse("Rana", 120, Arrays.asList(
                new Transaction(LocalDate.now().minusMonths(1), 150),
                new Transaction(LocalDate.now().minusMonths(2), 90)
        ));
        return Arrays.asList(response);
    }

    private RewardsResponse getSampleRewardsResponseForCustomer() {
        // A helper method to return a sample RewardsResponse for one customer
        return new RewardsResponse("Rana", 120, Arrays.asList(
                new Transaction(LocalDate.now().minusMonths(1), 150),
                new Transaction(LocalDate.now().minusMonths(2), 90)
        ));
    }
}
