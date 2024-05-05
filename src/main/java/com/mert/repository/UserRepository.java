package com.mert.repository;

import com.mert.entity.User;
import com.mert.utility.Repository;
import jakarta.persistence.TypedQuery;

import java.util.Optional;

public class UserRepository extends Repository<User, Long> {

    public UserRepository() {
        super(new User());
    }

    public boolean isUser(String userName) {
        boolean result = (boolean) getEm().createNativeQuery("SELECT count(*)>0 FROM TBLUSER WHERE user_name ='" + userName + "'").getSingleResult();
        return result;
    }

    public boolean isExist(String userName) {
        TypedQuery<Boolean> typedQuery = getEm().createNamedQuery("User.isExist", Boolean.class);
        typedQuery.setParameter("username", userName);
        return typedQuery.getSingleResult();
    }

    public Optional<User> findByUsernameAndPassword(String username, String password) {
        TypedQuery<User> typedQuery = getEm().createNamedQuery("User.findByUsernameAndPassword", User.class);
        typedQuery.setParameter("username", username);
        typedQuery.setParameter("password", password);
        return Optional.ofNullable(typedQuery.getSingleResult());
    }
}
