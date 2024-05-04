package com.mert.repository;

import com.mert.entity.User;
import com.mert.utility.Repository;

public class UserRepository extends Repository<User,LikeRepository> {

    public UserRepository() {
        super(new User());
    }
}
