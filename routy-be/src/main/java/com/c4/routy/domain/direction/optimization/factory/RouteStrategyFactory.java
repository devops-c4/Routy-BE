package com.c4.routy.domain.direction.optimization.factory;

import com.c4.routy.domain.direction.optimization.strategy.BruteForceStrategy;
import com.c4.routy.domain.direction.optimization.strategy.RouteStrategy;

public class RouteStrategyFactory {
    
    // 일정 개수 N에 따라서 다른 클래스를 반환해줌
    public RouteStrategy getRouteStrategy(int n){
        
        if(n < 6) {
            return new BruteForceStrategy();
        } else {
            return null;
        }
    }
}
