package com.c4.routy.domain.place.service;

import com.c4.routy.domain.place.dto.PlaceCreateRequestDTO;
import com.c4.routy.domain.place.entity.PlaceEntity;
import com.c4.routy.domain.place.mapper.PlaceQueryMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceQueryMapper placeQueryMapper;

    /** 장소 저장 */
    @Transactional
    public void savePlace(PlaceCreateRequestDTO dto) {
        placeQueryMapper.insertPlace(dto);
    }

    /** 일정에 포함된 장소 리스트 조회 */
    public List<PlaceEntity> getPlacesByPlanId(Integer planId) {
        return placeQueryMapper.findPlacesByPlanId(planId);
    }
}
