package com.c4.routy.domain.direction.optimization.strategy;

import com.c4.routy.domain.direction.dto.KakaoMobility.Location;

import java.util.List;

public interface RouteStrategy {
    List<Location> sort(List<Location> locations);
}
