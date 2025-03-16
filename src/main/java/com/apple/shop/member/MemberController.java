package com.apple.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

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

}
