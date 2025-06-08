package com.nbcamp.mypocketmovieapi.repository;

import com.nbcamp.mypocketmovieapi.entity.Content;
import org.apiguardian.api.API;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentJpaRepository extends JpaRepository<Content, Long> {

    List<Content> findByTitle(String title);
    List<Content> findByAuthorAndStatus(String author, API.Status status);

    @Query("SELECT c FROM Content c WHERE c.title LIKE %:keyword%")
    List<Content> searchByTitleKeyword(@Param("keyword") String keyword);
}
