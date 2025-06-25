package com.nbcamp.mypocketmovieapi.security;


import com.nbcamp.mypocketmovieapi.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

// 시큐리티가 제공하는 구현체를 사용
public class UserDetailsImpl implements UserDetails {
    private final Member member;

    public UserDetailsImpl(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return this.member;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getNickname();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
