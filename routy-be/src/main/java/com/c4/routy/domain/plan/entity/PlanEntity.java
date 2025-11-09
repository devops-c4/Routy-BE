package com.c4.routy.domain.plan.entity;

import com.c4.routy.domain.user.entity.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 여행 일정(플랜) Entity
// TBL_PLAN
// 여행의 최상위 단위 (여행 제목, 기간, 지역 등)

@Entity
@Table(name = "TBL_PLAN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Integer planId;

    @Column(name = "plan_title", nullable = false)
    private String planTitle;

    @Column(name = "is_shared", nullable = false)
    private boolean shared;

    @Column(name = "start_time", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_time", nullable = false)
    private LocalDate endDate;

    @Column(name = "is_deleted", nullable = false)
    private boolean deleted = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "bookmark_count")
    private Integer bookmarkCount = 0;

    @Column(name = "view_count")
    private Integer viewCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private RegionEntity region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no")
    private UserEntity user;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DurationEntity> durations = new ArrayList<>();

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewEntity> reviews = new ArrayList<>();

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /* 편의 메서드 */
    public void changeTitle(String title) { this.planTitle = title; }
    public void changePeriod(LocalDate start, LocalDate end) {
        this.startDate = start;
        this.endDate = end;
    }
}
