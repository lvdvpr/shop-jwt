package com.apple.shop.member;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    static final SecretKey key =
            Keys.hmacShaKeyFor(Decoders.BASE64.decode(
                    "jwtpassword1234jwtpassword1234jwtpassword1234jwtpassword1234jwtpassword1234"
            ));

    // JWT 만들어주는 함수
    public static String createToken(Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        // 권한 리스트를 authorities라는 변수에 문자로 담아준다.
        String authorities = auth.getAuthorities().stream().map( a -> a.getAuthority())
                .collect(Collectors.joining(","));
        // .claim(이름, 값)으로 jwt에 데이터 추가 가능
        String jwt = Jwts.builder()
                .claim("username", user.getUsername())
                .claim("displayName", user.getDisplayName())
                .claim("authorities", authorities)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(key)
                .compact();
        return jwt;
    }

    // JWT 까주는 함수
    public static Claims extractToken(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

        return claims;
    }
}
