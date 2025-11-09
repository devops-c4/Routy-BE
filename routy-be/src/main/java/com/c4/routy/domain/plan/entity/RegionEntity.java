package com.c4.routy.domain.plan.entity;

import jakarta.persistence.*;
import lombok.*;


//  여행 지역 (TBL_REGION)
@Entity
@Table(name = "TBL_REGION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private Integer regionId;

    @Column(name = "region_name", nullable = false)
    private String regionName;
}
