package com.nbcamp.mypocketmovieapi.repository;

import com.nbcamp.mypocketmovieapi.entity.Content;
import com.nbcamp.mypocketmovieapi.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentJpaRepository extends JpaRepository<Content, Long> {

    Content save(Content content);
    List<Content> findByMember(Member member);
    List<Content> findDistinctByExternalIdAndMember_Id(String externalId, Long memberid);

}
