package com.c4.routy.domain.plan.service;

import com.c4.routy.domain.plan.dto.PlanReviewFormDTO;
import com.c4.routy.domain.plan.dto.PlanReviewResponseDTO;
import com.c4.routy.domain.plan.dto.PlanReviewUploadRequestDTO;
import com.c4.routy.domain.plan.dto.ReviewFileDTO;
import com.c4.routy.domain.plan.entity.ReviewEntity;
import com.c4.routy.domain.plan.entity.ReviewFileEntity;
import com.c4.routy.domain.plan.mapper.PlanQueryMapper;
import com.c4.routy.domain.plan.repository.ReviewFileRepository;
import com.c4.routy.domain.plan.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//리뷰 서비스 구현체
// MyBatis 조회 / JPA 등록·수정

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final PlanQueryMapper planQueryMapper;
    private final ReviewRepository reviewRepository;
    private final ReviewFileRepository reviewFileRepository;

    /**
     * 리뷰 작성 모달 데이터 조회
     * (MyBatis로 기존 리뷰 + 파일 정보 가져오기)
     */
    @Override
    public PlanReviewFormDTO getReviewForm(Integer planId) {
        return planQueryMapper.selectReviewForm(planId);
    }

    /**
     * 리뷰 생성/수정 (파일 포함)
     */
    @Override
    @Transactional
    public PlanReviewResponseDTO createOrUpdateReview(PlanReviewUploadRequestDTO dto, Integer loginUserId) {

        // 1️⃣ ReviewEntity 생성 또는 수정
        ReviewEntity review = dto.getReviewId() == null
                ? new ReviewEntity()  // 신규 리뷰
                : reviewRepository.findById(dto.getReviewId())
                .orElse(new ReviewEntity()); // 없으면 신규 생성

        // (planId, userId는 컨트롤러나 서비스단에서 주입)
        review.setContent(dto.getContent());
        review.setRating(dto.getRating());

        reviewRepository.save(review);

        // 2️⃣ 리뷰 파일 저장
        if (dto.getFiles() != null && !dto.getFiles().isEmpty()) {
            for (MultipartFile file : dto.getFiles()) {
                // 실제로는 S3 업로드 로직 필요. 지금은 파일명으로 가짜 URL 생성.
                String fileUrl = "/uploads/" + file.getOriginalFilename();

                ReviewFileEntity fileEntity = ReviewFileEntity.builder()
                        .review(review)
                        .url(fileUrl)
                        .originalName(file.getOriginalFilename())
                        .build();

                reviewFileRepository.save(fileEntity);
            }
        }

        // 3️⃣ 응답 DTO 조립 (방금 저장한 리뷰 + 파일들)
        return PlanReviewResponseDTO.builder()
                .reviewId(review.getReviewId())
                .planId(review.getPlan() != null ? review.getPlan().getPlanId() : dto.getPlanId())
                .content(review.getContent())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt() != null ? review.getCreatedAt().toString() : null)
                .files(review.getFiles() != null
                        ? review.getFiles().stream()
                        .map(f -> ReviewFileDTO.builder()
                                .fileId(f.getFileId())
                                .url(f.getUrl())
                                .originalName(f.getOriginalName())
                                .build())
                        .toList()
                        : List.of()
                )
                .build();
    }
}
