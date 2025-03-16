package com.apple.shop.member;

import lombok.RequiredArgsConstructor;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveMember(String username, String password, String displayName) throws Exception {
        var result = memberRepository.findByUsername(username);
        if (result.isPresent()) {
            throw new Exception("존재하는아이디");
        }
        if (username.length() < 3 || password.length() < 4) {
            throw new Exception("너무짧음");
        }
        Member member = new Member();
        member.setUsername(username);
        var hashPassword = passwordEncoder.encode(password);
        member.setPassword(hashPassword);
        member.setDisplayName(displayName);
        memberRepository.save(member);
    }

    public MemberDto getUser() {
        var a = memberRepository.findById(1l);
        var result = a.get();
        var memberDto = new MemberDto(result.getUsername(), result.getDisplayName());
        return memberDto;
    }
}
