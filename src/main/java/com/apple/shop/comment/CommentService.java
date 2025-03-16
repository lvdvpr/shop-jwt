package com.apple.shop.comment;

import com.apple.shop.member.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void saveComment(String content, Long parentId, Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        var comment = new Comment();
        comment.setContent(content);
        comment.setUsername(user.getUsername());
        comment.setParentId(parentId);
        commentRepository.save(comment);
    }

    public List<Comment> findAllCommentById(Long parentId) {
        List<Comment> commentList = commentRepository.findAllByParentId(parentId);
        return commentList;
    }

}
