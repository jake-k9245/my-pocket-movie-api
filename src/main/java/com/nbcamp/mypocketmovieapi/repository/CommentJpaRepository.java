package com.nbcamp.mypocketmovieapi.repository;

import com.nbcamp.mypocketmovieapi.entity.Comment;
import com.nbcamp.mypocketmovieapi.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
    // select * from comments; findAll()
    // select * from comments where id = ?; findById(commentId);

    Page<Comment> findByReview(Review review, Pageable pageable);
}


