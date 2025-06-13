package com.nbcamp.mypocketmovieapi.repository;


import com.nbcamp.mypocketmovieapi.entity.Member;
import com.nbcamp.mypocketmovieapi.entity.Review;
import com.nbcamp.mypocketmovieapi.entity.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewLikeJpaRepository extends JpaRepository<ReviewLike, Long> {
    Optional<ReviewLike> findByMemberAndReview(Member member, Review review);
    List<ReviewLike> findByMember(Member member);
}

