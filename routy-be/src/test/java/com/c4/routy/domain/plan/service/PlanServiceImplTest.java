package com.c4.routy.domain.plan.service;

import com.c4.routy.domain.plan.dto.BrowseDetailResponseDTO;
import com.c4.routy.domain.plan.dto.PlanDetailResponseDTO;
import com.c4.routy.domain.plan.mapper.PlanMapper;
import com.c4.routy.domain.plan.repository.PlanRepository;
import com.c4.routy.domain.duration.repository.DurationRepository;
import com.c4.routy.domain.plan.repository.TravelRepository;
import com.c4.routy.domain.region.repository.RegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PlanServiceImplTest {

    @Mock private PlanMapper planMapper;

    @InjectMocks
    private PlanServiceImpl planService;

    private PlanDetailResponseDTO samplePlan;

    @BeforeEach
    void setup() {
        samplePlan = new PlanDetailResponseDTO();
        samplePlan.setPlanId(1);
        samplePlan.setStartDate("2025-05-01");
        samplePlan.setEndDate("2025-05-03");
    }

    // -----------------------------------------------------------
    @Test
    @DisplayName("상세 조회 - 정상적으로 일수 계산 및 상태 반환")
    void getPlanDetail_success() {
        // given
        given(planMapper.selectPlanDetail(1)).willReturn(samplePlan);

        // when
        PlanDetailResponseDTO result = planService.getPlanDetail(1);

        // then
        assertEquals(3, result.getDays());    // 5/1~5/3 → 3일
        assertEquals(2, result.getNights());
        assertNotNull(result.getStatus());
        assertTrue(result.isEditable());
    }

    @Test
    @DisplayName("상세 조회 - 존재하지 않는 일정이면 예외 발생")
    void getPlanDetail_notFound() {
        given(planMapper.selectPlanDetail(99)).willReturn(null);
        assertThrows(IllegalArgumentException.class, () -> planService.getPlanDetail(99));
    }

    // -----------------------------------------------------------
    @Test
    @DisplayName("좋아요 토글 - 없을 경우 추가, 있을 경우 취소")
    void toggleLike_success() {
        // given
        Integer planId = 10;
        Integer userId = 5;

        given(planMapper.selectPlanAuthorId(planId)).willReturn(99);
        given(planMapper.checkUserLike(planId, userId)).willReturn(false);

        // when
        String msg = planService.toggleLike(planId, userId);

        // then
        assertEquals("좋아요 추가", msg);
        verify(planMapper, times(1)).insertLike(planId, userId);

        // when (다시 누를 경우)
        given(planMapper.checkUserLike(planId, userId)).willReturn(true);
        String msg2 = planService.toggleLike(planId, userId);
        assertEquals("좋아요 취소", msg2);
        verify(planMapper, times(1)).deleteLike(planId, userId);
    }

    @Test
    @DisplayName("좋아요 - 본인이면 예외 발생")
    void toggleLike_selfLikeThrowsError() {
        Integer planId = 10, userId = 99;
        given(planMapper.selectPlanAuthorId(planId)).willReturn(userId);
        assertThrows(IllegalArgumentException.class, () -> planService.toggleLike(planId, userId));
    }

    // -----------------------------------------------------------
    @Test
    @DisplayName("조회수 증가 - 작성자 아닐 때만 증가")
    void increaseViewCount_nonAuthor() {
        given(planMapper.selectPlanAuthorId(1)).willReturn(9);
        planService.increaseViewCount(1, 5);
        verify(planMapper, times(1)).incrementViewCount(1);
    }

    @Test
    @DisplayName("조회수 증가 - 작성자거나 비로그인일 땐 증가하지 않음")
    void increaseViewCount_authorOrNull() {
        given(planMapper.selectPlanAuthorId(1)).willReturn(5);
        planService.increaseViewCount(1, 5); // 본인
        planService.increaseViewCount(1, null); // 비로그인
        verify(planMapper, never()).incrementViewCount(1);
    }

    // -----------------------------------------------------------
    @Test
    @DisplayName("북마크 토글 - 없을 경우 추가, 있을 경우 취소 및 카운트 갱신")
    void toggleBookmark_success() {
        Integer planId = 7, userId = 3;

        given(planMapper.checkUserBookmark(planId, userId)).willReturn(false);

        String msg = planService.toggleBookmark(planId, userId);
        assertEquals("북마크 추가", msg);
        verify(planMapper).insertBookmark(planId, userId);
        verify(planMapper).updateBookmarkCount(planId);

        // 다시 북마크 취소 케이스
        given(planMapper.checkUserBookmark(planId, userId)).willReturn(true);
        String msg2 = planService.toggleBookmark(planId, userId);
        assertEquals("북마크 취소", msg2);
        verify(planMapper).deleteBookmark(planId, userId);
    }

    // -----------------------------------------------------------
    @Test
    @DisplayName("공개 일정 상세 조회 - 리뷰와 Day/Activity 데이터 세팅")
    void getPublicPlanDetail_success() {
        BrowseDetailResponseDTO dto = new BrowseDetailResponseDTO();
        dto.setPlanId(10);
        dto.setReview(null);
        given(planMapper.selectPublicPlanDetail(10)).willReturn(dto);
        given(planMapper.selectPlanDays(10)).willReturn(Collections.emptyList());

        BrowseDetailResponseDTO result = planService.getPublicPlanDetail(10);

        assertNotNull(result);
        verify(planMapper).selectPublicPlanDetail(10);
        verify(planMapper).selectPlanDays(10);
    }
}
