package com.c4.routy.domain.plan.entity;

import jakarta.persistence.*;
import lombok.*;


// 하루 일정 내의 개별 여행 포인트 (TBL_TRAVEL)
// Duration과 1:N 관계
@Entity
@Table(name = "TBL_TRAVEL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "travel_id")
    private Integer travelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "duration_id")
    private DurationEntity duration;

    @Column(name = "title")
    private String title;               // 제주 공항 도착

    @Column(name = "place_name")
    private String placeName;           // 카카오 place_name

    @Column(name = "address_name")
    private String addressName;

    @Column(name = "category_group_name")
    private String categoryGroupName;

    @Column(name = "place_url")
    private String placeUrl;

    @Column(name = "tag")
    private String tag;                 // 숙소/식당/관광...

    @Column(name = "order_no")
    private Integer orderNo;

    @Column(name = "memo")
    private String memo;
    /** === 편의 메서드 === */
    public void updateFromDTO(com.c4.routy.domain.plan.dto.PlanEditSaveActivityDTO dto) {
        this.title = dto.getTitle();
        this.placeName = dto.getPlaceName();
        this.addressName = dto.getAddressName();
        this.categoryGroupName = dto.getCategoryGroupName();
        this.placeUrl = dto.getPlaceUrl();
        this.tag = dto.getTag();
        this.orderNo = dto.getOrderNo();
    }
}
