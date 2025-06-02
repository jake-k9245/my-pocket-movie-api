package com.nbcamp.mypocketmovieapi.repository;

import com.nbcamp.mypocketmovieapi.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistJpaRepository extends JpaRepository<Wishlist, Long> {

}
