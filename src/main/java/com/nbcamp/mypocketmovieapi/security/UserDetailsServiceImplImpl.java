package com.nbcamp.mypocketmovieapi.security;

import com.nbcamp.mypocketmovieapi.common.CommonCode;
import com.nbcamp.mypocketmovieapi.entity.Member;
import com.nbcamp.mypocketmovieapi.exception.member.MemberNotFoundException;
import com.nbcamp.mypocketmovieapi.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImplImpl implements UserDetailsService {
    private final MemberJpaRepository memberJpaRepository;

    // nickname으로 가져오지만 오버라이드한거라 메소드명은 못바꿈 (==loadUserByUsername)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberJpaRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(CommonCode.FAIL_INVALID_EMAIL));
        return new UserDetailsImpl(member);
    }

}
