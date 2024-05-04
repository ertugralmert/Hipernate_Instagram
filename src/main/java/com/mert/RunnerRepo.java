package com.mert;

import com.mert.entity.Comment;
import com.mert.repository.CommentRepository;

public class RunnerRepo {
    public static void main(String[] args) {
        CommentRepository commentRepository = new CommentRepository();
        commentRepository.save(Comment.builder()
                        .comment("Yeni bir yorum yaptım")
                        .date(System.currentTimeMillis())
                        .postid(1L)
                        .userid(2L)
                .build());
    }
}
