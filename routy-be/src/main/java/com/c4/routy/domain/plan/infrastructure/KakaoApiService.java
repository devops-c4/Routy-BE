package com.c4.routy.domain.plan.infrastructure;

import com.c4.routy.domain.plan.dto.KakaoMobility.KakaoRouteRequest;
import com.c4.routy.domain.plan.dto.KakaoMobility.KakaoRouteResponse;
import com.c4.routy.domain.plan.dto.KakaoMobility.Location;

public interface KakaoApiService {
    KakaoRouteResponse getDirection(KakaoRouteRequest request);
    int getTimes(Location origin, Location destination);
}
