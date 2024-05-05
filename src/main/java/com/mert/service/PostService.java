package com.mert.service;

import com.mert.entity.Post;
import com.mert.entity.User;
import com.mert.repository.PostRepository;
import com.mert.repository.UserRepository;

import java.util.Optional;

public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public PostService(){
     this.postRepository = new PostRepository();
     this.userRepository = new UserRepository();

    }

    public void addNewPost(Long userId, String comment,String imageurl,String location){
        Optional<User> user = userRepository.findyById(userId);
        if(user.isEmpty())return; // Eğer user yok ise işlemi bitir.
        postRepository.save(Post.builder()
                        .comment(comment)
                        .commentcount(0)
                        .imageurl(imageurl)
                        .likes(0)
                        .location(location)
                        .shareddate(System.currentTimeMillis())
                        .userid(userId)
                .build());

    }
}
