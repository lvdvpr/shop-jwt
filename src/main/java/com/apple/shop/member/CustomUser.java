package com.apple.shop.member;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
@Setter
public class CustomUser extends User {

    private Long id;
    private String displayName;
    public CustomUser(String username,
                      String password,
                      List<GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}