package com.c4.routy.domain.place.controller;

import com.c4.routy.domain.place.dto.PlaceCreateRequestDTO;
import com.c4.routy.domain.place.entity.PlaceEntity;
import com.c4.routy.domain.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/places")
public class PlaceController {

    private final PlaceService placeService;

    /** 장소 추가 (Kakao API → 일정에 저장) */
    @PostMapping
    public ResponseEntity<String> addPlace(@RequestBody PlaceCreateRequestDTO dto) {
        placeService.savePlace(dto);
        return ResponseEntity.ok("장소 저장 완료");
    }

    /** 일정에 등록된 장소 조회 */
    @GetMapping("/{planId}")
    public ResponseEntity<List<PlaceEntity>> getPlaces(@PathVariable Integer planId) {
        List<PlaceEntity> places = placeService.getPlacesByPlanId(planId);
        return ResponseEntity.ok(places);
    }
}