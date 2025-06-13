package com.nbcamp.mypocketmovieapi.repository;

import com.nbcamp.mypocketmovieapi.entity.Member;
import com.nbcamp.mypocketmovieapi.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistJpaRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByMember(Member member);
    boolean existsByMemberAndExternalId(Member member, String externalId);
}
