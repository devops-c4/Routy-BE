package com.c4.routy.domain.plan.service;

import com.c4.routy.domain.plan.dto.KakaoMobility.KakaoRouteRequest;
import com.c4.routy.domain.plan.dto.KakaoMobility.KakaoRouteResponse;
import com.c4.routy.domain.plan.dto.KakaoMobility.OptimizationRequest;
import com.c4.routy.domain.plan.dto.KakaoMobility.OptimizationResponse;

public interface MapService {
    KakaoRouteResponse getDirection(KakaoRouteRequest request);

    OptimizationResponse getOptimization(OptimizationRequest request);
}
