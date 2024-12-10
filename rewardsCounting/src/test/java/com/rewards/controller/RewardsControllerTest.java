package com.rewards.controller;

import com.rewards.model.RewardsResponse;
import com.rewards.service.RewardsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RewardsController.class)
public class RewardsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardsService rewardsService;

    @Test
    public void testGetRewards() throws Exception {
        when(rewardsService.calculateRewards()).thenReturn(Arrays.asList(
                new RewardsResponse("Alice", 10, 90),
                new RewardsResponse("Bob", 11, 110)
        ));

        mockMvc.perform(get("/rewards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerName").value("Alice"))
                .andExpect(jsonPath("$[0].month").value(10))
                .andExpect(jsonPath("$[0].points").value(90))
                .andExpect(jsonPath("$[1].customerName").value("Bob"))
                .andExpect(jsonPath("$[1].month").value(11))
                .andExpect(jsonPath("$[1].points").value(110));
    }
}