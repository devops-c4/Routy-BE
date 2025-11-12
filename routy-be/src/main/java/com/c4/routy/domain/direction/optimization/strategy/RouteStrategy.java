package com.c4.routy.domain.direction.optimization.strategy;

import com.c4.routy.domain.direction.dto.KakaoMobility.Location;

import java.util.List;

public abstract  class RouteStrategy {
    public List<Location> sort(List<Location> locations, List<Integer> fixed, int[][] weight) {

        // 1. 최적 경로 탐색
        List<Integer> bestOrder = findOptimalOrder(fixed, weight);

        // 2. 순서에 따라 Location 정렬 후 반환
        return bestOrder.stream()
                .map(locations::get)
                .toList();
    }

    // 구현체에서만 오버라이드
    protected abstract List<Integer> findOptimalOrder(List<Integer> fixed, int[][] weight);
}
