package com.c4.routy.domain.plan.dto;

import lombok.*;


//상세보기에서 일정(활동) 한 줄
//수정 화면에서는 비슷한 구조의 PlanEditActivityDTO 를 따로 씀
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanActivityDTO {
    private Integer travelId;     // TBL_TRAVEL.travel_id (없으면 null)
    private String title;         // 활동 제목
    private String place;         // 제주국제공항, 흑돼지 거리 ... (장소명)

    private String tag;           // 숙소 / 카페 / 식당 / 관광 / 기타 ... (아이콘 바인딩용 태그)
    private String url;           // 여행지 정보url



    //일정수정 변경사항
    // address_name(여행장소), category_group_name(카테고리), plcae_name(여행지 제목), plcae_url(여행지 정보url)
    //장소명, 장소 주소, 장소 카테고리, 장소 정보 url 뿌려주기로함
}
