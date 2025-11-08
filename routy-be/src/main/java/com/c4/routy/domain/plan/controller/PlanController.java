package com.c4.routy.domain.plan.controller;

import com.c4.routy.domain.plan.dto.PlanDetailResponseDTO;
import com.c4.routy.domain.plan.dto.PlanSummaryResponseDTO;
import com.c4.routy.domain.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plan")
public class PlanController {

    private final PlanService planService;

    /**
     * 일정 상세 조회
     */
    @GetMapping("/{planId}")
    public PlanDetailResponseDTO getPlanDetail(@PathVariable Integer planId) {
        return planService.getPlanDetail(planId);
    }

    /**
     * 특정 사용자의 일정 목록 조회 (mypage에 '내 일정' section부분)
     */
    @GetMapping("/list/{userId}")
    public List<PlanSummaryResponseDTO> getUserPlans(@PathVariable Integer userId) {
        return planService.getUserPlans(userId);
    }
}
