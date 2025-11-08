package com.c4.routy.domain.plan.service;

import com.c4.routy.domain.plan.dto.KakaoMobility.KakaoRouteRequest;
import com.c4.routy.domain.plan.dto.KakaoMobility.KakaoRouteResponse;

public interface MapService {
    KakaoRouteResponse getDirection(KakaoRouteRequest request);

    KakaoRouteResponse getOptimization(KakaoRouteRequest request);
}
