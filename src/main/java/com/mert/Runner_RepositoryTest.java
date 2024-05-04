package com.mert;

import com.mert.entity.User;
import com.mert.entity.UserProfile;
import com.mert.repository.UserRepository;

public class Runner_RepositoryTest {

    public static void main(String[] args) {

        User user = User.builder()
                .username("mert")
                .build();

        UserRepository userRepository = new UserRepository();
        userRepository.findAllByEntity(user).forEach(System.out::println);



    }
}
