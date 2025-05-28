package com.nbcamp.mypocketmovieapi.repository;

import com.nbcamp.mypocketmovieapi.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListJpaRepository extends JpaRepository<WishList, Long> {

}
