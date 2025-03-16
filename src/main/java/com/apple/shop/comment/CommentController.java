package com.apple.shop.comment;

import com.apple.shop.member.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/addComment")
    String addComment (@RequestParam String content, @RequestParam Long parentId, Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }
        commentService.saveComment(content, parentId, auth);
        return "redirect:/detail/" + parentId;
    }
}
