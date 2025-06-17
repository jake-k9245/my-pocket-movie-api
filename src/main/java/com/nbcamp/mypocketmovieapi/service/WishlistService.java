package com.nbcamp.mypocketmovieapi.service;

import com.nbcamp.mypocketmovieapi.common.CommonCode;
import com.nbcamp.mypocketmovieapi.dto.WishlistResponseDto;
import com.nbcamp.mypocketmovieapi.entity.Content;
import com.nbcamp.mypocketmovieapi.entity.Member;
import com.nbcamp.mypocketmovieapi.entity.Wishlist;
import com.nbcamp.mypocketmovieapi.exception.content.ContentNotFoundException;
import com.nbcamp.mypocketmovieapi.exception.member.MemberNotFoundException;
import com.nbcamp.mypocketmovieapi.exception.wishlist.DuplicateWishlistException;
import com.nbcamp.mypocketmovieapi.exception.wishlist.UnAuthorizedWishlistException;
import com.nbcamp.mypocketmovieapi.exception.wishlist.WishlistNotFoundException;
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
                () -> new MemberNotFoundException(CommonCode.FAIL_MEMBER_NOT_FOUND)
        );

        Content content = contentJpaRepository.findById(contentId).orElseThrow(
                () -> new ContentNotFoundException(CommonCode.FAIL_CONTENT_NOT_FOUND)
        );

        if (wishlistJpaRepository.existsByMemberAndExternalId(member, content.getExternalId())) {
            throw new DuplicateWishlistException(CommonCode.FAIL_DUPLICATE_WISHLIST);
        }

        Wishlist wishlist = new Wishlist(content.getExternalId(), member, content);
        wishlistJpaRepository.save(wishlist);
    }

    public List<WishlistResponseDto> getWishlists(Long memberId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(
                () -> new MemberNotFoundException(CommonCode.FAIL_MEMBER_NOT_FOUND)
        );
        List<Wishlist> wishlists = wishlistJpaRepository.findByMember((member));
        return wishlists.stream().map(WishlistResponseDto::new).collect(Collectors.toList());
    }

    public void deleteWishlist(Long memberId, Long wishlistId) {
        Member member = memberJpaRepository.findById(memberId).orElseThrow(
                () -> new MemberNotFoundException(CommonCode.FAIL_MEMBER_NOT_FOUND)
        );

        Wishlist wishlist = wishlistJpaRepository.findById(wishlistId).orElseThrow(
                () -> new WishlistNotFoundException(CommonCode.FAIL_WISHLIST_NOT_FOUND)
        );

        if (!Objects.equals(member.getId(), wishlist.getMember().getId())) {
            throw new UnAuthorizedWishlistException(CommonCode.FAIL_UNAUTHORIZED_WISHLIST_DELETION);
        }

        wishlistJpaRepository.delete(wishlist);
    }

}
