package com.nbcamp.mypocketmovieapi.service;

import com.nbcamp.mypocketmovieapi.dto.WishlistResponseDto;
import com.nbcamp.mypocketmovieapi.entity.Content;
import com.nbcamp.mypocketmovieapi.entity.Member;
import com.nbcamp.mypocketmovieapi.entity.Wishlist;
import com.nbcamp.mypocketmovieapi.repository.ContentJpaRepository;
import com.nbcamp.mypocketmovieapi.repository.MemberJpaRepository;
import com.nbcamp.mypocketmovieapi.repository.WishlistJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final MemberJpaRepository memberJpaRepository;
    private final ContentJpaRepository contentJpaRepository;
    private final WishlistJpaRepository wishlistJpaRepository;

    public void saveWishlist(Long memberId, Long contentId) {
       Member member = memberJpaRepository.findById(memberId).orElseThrow(
                () -> new RuntimeException("해당 회원을 찾을 수 없습니다.")
       );

       Content content = contentJpaRepository.findById(contentId).orElseThrow(
               () -> new RuntimeException("해당 영화를 찾을 수 없습니다.")
       );

       String externalId = "";
       if (wishlistJpaRepository.existsByMemberAndExternalId(member, externalId)) {
           throw new RuntimeException("이미 등록한 콘텐츠를 위시리스트에 다시 추가할 수 없습니다.");
       }

       Wishlist wishlist = new Wishlist("external", member, content);
       wishlistJpaRepository.save(wishlist);
    }

    public List<WishlistResponseDto> getWishlists(Long memberId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(
                () -> new RuntimeException("해당 회원을 찾을 수 없습니다.")
        );
        List<Wishlist> wishlists = wishlistJpaRepository.findByMember((member));
        return wishlists.stream().map(WishlistResponseDto::new).collect(Collectors.toList());
    }

    public void deleteWishlist(Long memberId, Long wishlistId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(
                () -> new RuntimeException("해당 회원을 찾을 수 없습니다.")
        );

        Wishlist wishlist = wishlistJpaRepository.findById(wishlistId).orElseThrow(
                () -> new RuntimeException("해당 위시리스트가 존재하지 않습니다.")
        );

        if(!Objects.equals(member.getId(), wishlist.getMember().getId())) {
            throw new RuntimeException("등록한 회원만 위시리스트를 삭제할 수 있습니다.");
        }

        wishlistJpaRepository.delete(wishlist);
    }

}
