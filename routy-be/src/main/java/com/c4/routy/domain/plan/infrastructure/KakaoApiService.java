package com.c4.routy.domain.plan.infrastructure;

import com.c4.routy.domain.plan.dto.KakaoMobility.KakaoRouteRequest;
import com.c4.routy.domain.plan.dto.KakaoMobility.KakaoRouteResponse;

public interface KakaoApiService {
    KakaoRouteResponse getDirection(KakaoRouteRequest request);
}
