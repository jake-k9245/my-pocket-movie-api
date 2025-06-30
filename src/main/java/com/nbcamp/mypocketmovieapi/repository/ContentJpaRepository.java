package com.nbcamp.mypocketmovieapi.repository;

import com.nbcamp.mypocketmovieapi.entity.Content;
import com.nbcamp.mypocketmovieapi.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentJpaRepository extends JpaRepository<Content, Long> {
    Page<Content> findByMember(Member member, Pageable pageable);
    List<Content> findDistinctByExternalIdAndMember_Id(String externalId, Long memberid);
}