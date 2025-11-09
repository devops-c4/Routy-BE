package com.c4.routy.domain.plan.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


//  일정 내 'Day' 단위 (TBL_DURATION)
//  Day 1, Day 2 ...
//  Plan과 1:N 관계
@Entity
@Table(name = "TBL_DURATION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "duration_id")
    private Integer durationId;

    @Column(name = "day_no", nullable = false)
    private Integer dayNo;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private PlanEntity plan;

    @OneToMany(mappedBy = "duration", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TravelEntity> travels = new ArrayList<>();

    public void addTravel(TravelEntity travel) {
        this.travels.add(travel);
        travel.setDuration(this);
    }
}
