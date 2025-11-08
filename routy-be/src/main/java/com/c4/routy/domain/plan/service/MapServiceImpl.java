package com.c4.routy.domain.plan.service;

import com.c4.routy.domain.plan.dto.KakaoMobility.KakaoRouteRequest;
import com.c4.routy.domain.plan.dto.KakaoMobility.KakaoRouteResponse;
import com.c4.routy.domain.plan.infrastructure.KakaoApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {

    private final KakaoApiService kakaoApiService;


    // TODO: API 호출 횟수를 줄이기 위한 캐싱 로직 추가
    /*
     * Kakao API에서 반환되는 고유 routeId(또는 요청 파라미터 조합)를 키로 사용해,
     * 응답 데이터를 로컬 파일이나 캐시로 저장하는 방식을 고려한다.
     *
     * - 동일한 출발지/도착지 요청이 들어올 경우,
     *   API 재호출 대신 기존 저장 데이터를 반환하도록 처리.
     * - 단, 경로 재탐색이 필요한 상황(예: 위치 변경, 옵션 변경 등)에서는
     *   기존 캐시를 무시하고 새로 호출하도록 한다.
     */
    @Override
    public KakaoRouteResponse getDirection(KakaoRouteRequest request) {
        return kakaoApiService.getDirection(request);
    }

    @Override
    public KakaoRouteResponse getOptimization(KakaoRouteRequest request) {
        return null;
    }
}
