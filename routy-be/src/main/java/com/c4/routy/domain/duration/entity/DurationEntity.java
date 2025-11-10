package com.c4.routy.domain.duration.entity;

import com.c4.routy.domain.plan.entity.PlanEntity;
import com.c4.routy.domain.plan.entity.TravelEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TBL_DURATION")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "duration_id")
    private Integer durationId;

    @Column(name = "day_no", nullable = false)
    private Integer dayNo;

    @Column(name = "date", nullable = false)
    private String date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private PlanEntity plan;

    @OneToMany(mappedBy = "duration", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelEntity> travels = new ArrayList<>();
}
