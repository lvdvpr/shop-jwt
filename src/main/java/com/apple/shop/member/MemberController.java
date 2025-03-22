package com.apple.shop.member;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @GetMapping("/register")
    public String register(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            return "redirect:/";
        } else {
            return "register.html";
        }
    }

    @PostMapping("/member")
    public String addMember(String username, String password, String displayName) throws Exception {
        memberService.saveMember(username, password, displayName);
        return "redirect:/list";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/mypage")
    public String myPage(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            CustomUser result = (CustomUser) auth.getPrincipal();
            return "mypage.html";
        } else {
            return "login.html";
        }
    }

    @GetMapping("/user/1")
    @ResponseBody
    public MemberDto getUser() {
        MemberDto memberDto = memberService.getUser();
        return memberDto;
    }

    @PostMapping("/login/jwt")
    @ResponseBody
    public String loginJWT(@RequestBody Map<String, String> data,
                           HttpServletResponse response) {

        // 유저가 보낸 아이디, 비번을 토큰에 넣는다
        var authToken = new UsernamePasswordAuthenticationToken(
                data.get("username"), data.get("password"));
        // authenticationManagerBuilder에 아이디와 비번이 들어있는 토큰을 넣으면 아이디/비번을 db내용과 비교해서 로그인해준다.
        var auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        // Authentication auth 변수에 유저정보가 추가된다. 앞으로 SecurityContextHolder.getContext().getAuthentication();을 사용해 사용자정보를 가져올 수 있다.
        SecurityContextHolder.getContext().setAuthentication(auth);

        var jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());
        System.out.println(jwt);

        // 서버에서 쿠키에 jwt 저장하는 방법, 쿠키 저장소에는 이름과 값만 저장할 수 있다.
        var cookie = new Cookie("jwt", jwt);
        // 쿠키의 유효기간, jwt의 유효기간과 비슷하거나 조금 길게 만들면 됨
        cookie.setMaxAge(100000);
        // 쿠키를 자바스크립트로 조작하는 것을 막는다.
        cookie.setHttpOnly(true);
        // 쿠키가 전송될 URL을 제한, /는 모든 경로로 전송
        cookie.setPath("/");
        // 유저 브라우저에 쿠키가 저장된다.
        response.addCookie(cookie);

        return jwt;
    }

    @GetMapping("/mypage/jwt")
    @ResponseBody
    public String myPageJWT(Authentication auth) {

        var user = (CustomUser) auth.getPrincipal();
        System.out.println(user);
        System.out.println(user.getDisplayName());
        System.out.println(user.getAuthorities());

        return "마이페이지 데이터";
    }


}
