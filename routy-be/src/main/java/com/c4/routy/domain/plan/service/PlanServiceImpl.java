package com.c4.routy.domain.plan.service;

import com.c4.routy.domain.plan.dto.*;
import com.c4.routy.domain.plan.entity.DurationEntity;
import com.c4.routy.domain.plan.entity.PlanEntity;
import com.c4.routy.domain.plan.entity.RegionEntity;
import com.c4.routy.domain.plan.entity.TravelEntity;
import com.c4.routy.domain.plan.mapper.PlanQueryMapper;
import com.c4.routy.domain.plan.repository.DurationRepository;
import com.c4.routy.domain.plan.repository.PlanRepository;
import com.c4.routy.domain.plan.repository.RegionRepository;
import com.c4.routy.domain.plan.repository.TravelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService{

    private final PlanQueryMapper planQueryMapper;
    private final PlanRepository planRepository;
    private final DurationRepository durationRepository;
    private final TravelRepository travelRepository;
    private final RegionRepository regionRepository;

    @Override
    public PlanDetailResponseDTO getPlanDetail(Integer planId) {
        return planQueryMapper.selectPlanDetail(planId);
    }

    @Override
    public PlanEditResponseDTO getPlanEdit(Integer planId) {
        return planQueryMapper.selectPlanEdit(planId);
    }

    /**
     * 일정 수정 저장 로직
     * - 기존 Day/Travel 싹 지우고 새로 INSERT 하는 방식 (가장 단순, 프론트 구조랑 1:1 매칭)
     */
    @Override
    @Transactional
    public void updatePlan(PlanEditSaveRequestDTO dto) {

        PlanEntity plan = planRepository.findById(dto.getPlanId())
                .orElseThrow(() -> new IllegalArgumentException("플랜을 찾을 수 없습니다."));

        plan.changeTitle(dto.getTitle());
        plan.changePeriod(LocalDate.parse(dto.getStartDate()), LocalDate.parse(dto.getEndDate()));

        if (dto.getDestination() != null) {
            RegionEntity region = regionRepository.findByRegionName(dto.getDestination());
            plan.setRegion(region);
        }

        // 기존 Day/Travel 다 삭제
        List<DurationEntity> oldDays = durationRepository.findByPlan(plan);
        for (DurationEntity oldDay : oldDays) {
            travelRepository.deleteByDuration(oldDay);
        }
        durationRepository.deleteByPlan(plan);

        // 새로 insert
        for (PlanEditSaveDayDTO dayDTO : dto.getDayList()) {
            DurationEntity dayEntity = DurationEntity.builder()
                    .plan(plan)
                    .dayNo(dayDTO.getDayNo())
                    .date(LocalDate.parse(dayDTO.getDate()))
                    .build();
            durationRepository.save(dayEntity);

            for (PlanEditSaveActivityDTO actDTO : dayDTO.getActivities()) {
                TravelEntity travel = TravelEntity.builder()
                        .duration(dayEntity)
                        .title(actDTO.getTitle())
                        .placeName(actDTO.getPlaceName())
                        .addressName(actDTO.getAddressName())
                        .categoryGroupName(actDTO.getCategoryGroupName())
                        .placeUrl(actDTO.getPlaceUrl())
                        .tag(actDTO.getTag())
                        .orderNo(actDTO.getOrderNo())
                        .build();
                travelRepository.save(travel);
            }
        }

        planRepository.save(plan);
    }
}
