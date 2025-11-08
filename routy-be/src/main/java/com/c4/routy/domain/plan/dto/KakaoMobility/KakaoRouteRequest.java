package com.c4.routy.domain.plan.dto.KakaoMobility;

import com.c4.routy.domain.plan.enums.Priority;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class KakaoRouteRequest {

    private Location origin;
    private Location destination;

    @JsonProperty("waypoints")
    private List<Location> wayPoints;
    private Priority priority = Priority.RECOMMEND;
}
