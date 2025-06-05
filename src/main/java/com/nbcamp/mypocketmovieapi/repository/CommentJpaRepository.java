package com.nbcamp.mypocketmovieapi.repository;

import com.nbcamp.mypocketmovieapi.entity.Comment;
import com.nbcamp.mypocketmovieapi.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
    // select * from comments; findAll()
    // select * from comments where id = ?; findById(commentId);

    // 지금 필요한 SQL
    // select * from comments where review_id = ?; 기본 제공 X
    List<Comment> findByReview(Review review);
}


