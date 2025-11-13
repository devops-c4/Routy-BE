package com.c4.routy.domain.direction.service;

import com.c4.routy.domain.direction.dto.KakaoMobility.*;
import com.c4.routy.domain.direction.enums.Priority;
import com.c4.routy.domain.direction.infrastructure.KakaoApiService;
import com.c4.routy.domain.direction.optimization.context.RouteSorter;
import com.c4.routy.domain.direction.optimization.factory.RouteStrategyFactory;
import com.c4.routy.domain.direction.optimization.strategy.RouteStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MapServiceImplTest {

    private MapServiceImpl mapService;
    private KakaoApiService kakaoApiService;

    @BeforeEach
    void setUp() {
        kakaoApiService = mock(KakaoApiService.class);
        mapService = new MapServiceImpl(kakaoApiService);
    }

    @Test
    void testGetDirection_ReturnsApiResponse() {
        // given
        KakaoRouteRequest request = new KakaoRouteRequest();
        KakaoRouteResponse expectedResponse = new KakaoRouteResponse();

        when(kakaoApiService.getDirection(request)).thenReturn(expectedResponse);

        // when
        KakaoRouteResponse actual = mapService.getDirection(request);

        // then
        assertEquals(expectedResponse, actual);
        verify(kakaoApiService, times(1)).getDirection(request);
    }

    @Test
    void testGetOptimization_Success() {
        Location a = new Location(127.0, 37.0);
        Location b = new Location(127.1, 37.1);
        Location c = new Location(127.2, 37.2);

        List<Location> points = Arrays.asList(a, b, c);
        List<Integer> fixedPoints = List.of(1);

        // OptimizationRequest 생성자 수정 (priority와 roadEvent까지 전달)
        OptimizationRequest request = new OptimizationRequest(
                points,
                fixedPoints,
                Priority.RECOMMEND, // 기본값 유지
                2                   // roadEvent 기본값
        );

        // KakaoApiService.getTimes() Mock
        when(kakaoApiService.getTimes(any(), any())).thenReturn(10);

        // Mock 정렬 전략 (팩토리 정적 메서드 Stub)
        RouteStrategy mockStrategy = mock(RouteStrategy.class);
        when(mockStrategy.sort()).thenReturn(points); // 그대로 반환하도록 설정

        // KakaoRouteResponse Mock 데이터 생성
        KakaoRouteResponse mockRouteResponse = new KakaoRouteResponse();
        mockRouteResponse.setTransId("test-trans-id");
        mockRouteResponse.setRoutes(List.of()); // 임의로 빈 리스트

        // RouteStrategyFactory.getRouteStrategy() 정적 메서드 Mock
        try (MockedStatic<RouteStrategyFactory> factoryMock = Mockito.mockStatic(RouteStrategyFactory.class)) {
            factoryMock.when(() -> RouteStrategyFactory.getRouteStrategy(any(), any(), any(), anyInt()))
                    .thenReturn(mockStrategy);

            // KakaoApiService.getDirection() Mock
            when(kakaoApiService.getDirection(any())).thenReturn(mockRouteResponse);

            // when
            OptimizationResponse result = mapService.getOptimization(request);

            // then
            assertNotNull(result);
            assertEquals(points, result.getLocations(), "정렬된 위치 리스트가 일치해야 합니다.");
            assertEquals(mockRouteResponse, result.getRoute(), "경로 응답이 일치해야 합니다.");
        }

        // Verify Kakao API 호출 횟수 (getTimes는 n*(n-1)번 호출)
        verify(kakaoApiService, atLeastOnce()).getTimes(any(), any());
    }
}
