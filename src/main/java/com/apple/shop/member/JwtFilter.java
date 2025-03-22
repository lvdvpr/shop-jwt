package com.apple.shop.member;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Arrays;

public class JwtFilter extends OncePerRequestFilter {   // 요청마다 1회만 실행되게 하고 싶을 때 extends OncePerRequestFilter를 사용

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("필터실행됨");

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            filterChain.doFilter(request, response);    // 다음 필터를 실행한다.
            return;
        }

        var jwtCookie = "";
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals("jwt")) {
                jwtCookie = cookies[i].getValue();
            }
        }

        Claims claim;
        try {
            claim = JwtUtil.extractToken(jwtCookie);    // jwt가 유효한지 검사
        } catch (Exception e) {
            System.out.println("유효기간 만료되거나 이상함");
            filterChain.doFilter(request, response);
            return;
        }

        var arr = claim.get("authorities").toString().split(",");
        var authorities = Arrays.stream(arr)
                .map(a -> new SimpleGrantedAuthority(a)).toList();

        var customUser = new CustomUser(
                claim.get("username").toString(),
                "none", // 패스워드
                authorities
        );
        customUser.setDisplayName(claim.get("displayName").toString());

        var authToken = new UsernamePasswordAuthenticationToken(
                customUser, "", authorities
        );
        authToken.setDetails(new WebAuthenticationDetailsSource()
                .buildDetails(request)          // auth변수의 details를 추가해주는 부분
        );
        SecurityContextHolder.getContext().setAuthentication(authToken);    // auth 변수에 유저정보 넣기

        filterChain.doFilter(request, response);
    }

}