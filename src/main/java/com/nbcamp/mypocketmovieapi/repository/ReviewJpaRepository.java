package com.nbcamp.mypocketmovieapi.repository;

import com.nbcamp.mypocketmovieapi.entity.Content;
import com.nbcamp.mypocketmovieapi.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewJpaRepository extends JpaRepository<Review, Long> {

    List<Review> findByContent(Content content); // select * from reivews where content_id = 1;

}