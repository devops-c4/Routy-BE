package com.c4.routy.domain.plan.service;

import com.c4.routy.domain.plan.dto.PlanDetailResponseDTO;
import com.c4.routy.domain.plan.dto.PlanEditResponseDTO;
import com.c4.routy.domain.plan.dto.PlanEditSaveRequestDTO;
import org.springframework.stereotype.Service;


public interface PlanService {
    PlanDetailResponseDTO getPlanDetail(Integer planId);
    PlanEditResponseDTO getPlanEdit(Integer planId);
    void updatePlan(PlanEditSaveRequestDTO dto);
}
