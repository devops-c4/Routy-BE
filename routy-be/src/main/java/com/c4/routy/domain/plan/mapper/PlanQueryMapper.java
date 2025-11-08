package com.c4.routy.domain.plan.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface PlanQueryMapper {

    /* 🔹 상세보기 (기존) */
    List<Map<String, Object>> selectPlanDetailFlat(Integer planId);

    /* 🔹 마이페이지 – 내 일정 목록 */
    List<Map<String, Object>> selectUserPlans(Integer userId);
}
