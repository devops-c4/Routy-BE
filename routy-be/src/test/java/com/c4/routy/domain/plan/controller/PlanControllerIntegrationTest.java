package com.c4.routy.domain.plan.controller;

import com.c4.routy.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PlanControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetPublicPlans() throws Exception {
        mockMvc.perform(get("/api/plans/public")
                        .param("sort", "latest"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetPlanDetail() throws Exception {
        mockMvc.perform(get("/api/plans/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.planId").value(1));
    }

    @Test
    void testIncreaseViewCount() throws Exception {
        mockMvc.perform(post("/api/plans/1/view"))
                .andExpect(status().isOk());
    }
}
