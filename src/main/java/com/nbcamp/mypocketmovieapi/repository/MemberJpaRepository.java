package com.nbcamp.mypocketmovieapi.repository;

import com.nbcamp.mypocketmovieapi.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
}
