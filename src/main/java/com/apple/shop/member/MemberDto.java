package com.apple.shop.member;

public class MemberDto {
    public String username;
    public String displayName;
    MemberDto(String username, String displayName) {
        this.username = username;
        this.displayName = displayName;
    }
}
