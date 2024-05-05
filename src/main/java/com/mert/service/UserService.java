package com.mert.service;

import com.mert.entity.User;
import com.mert.repository.UserRepository;
import com.mert.utility.Response;

import java.util.List;
import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    // TODO: 1. kullanıcı adı daha önce kullanılmış olabilir.
    // TODO: 2. e-posta, aynı e-posta kullanılmış olabilir.
    // TODO: 3. Tanımsız beklenmeyen bir hata olabilir
    // Reponse clas oluştukturduk ve bundan sonra generic bir hata tanımlama class'ımız oldu.

    public Response<Boolean> register(String username, String email, String password) {
        /**
         * method retury type olmalı mı olmamalı mı :
         * 1- userName daha önce kullanılmış olabilir.
         * 2- email , aynı e posta kullanuılması sorun olabilir.
         * 3- Tanımsız beklenmeyen bir hata olabilir
         */
        // userName var mı yok mu sorgusu
        Response<Boolean> response = new Response<Boolean>();
        if (userRepository.isExist(username)) { // kullanıcı adı başka biri tarafından alınmış.
            response.setData(false);
            response.setMessage("Bu kullanıcı adı daha önce alınmış");
            response.setStatusCode(400);
            return response;
        } // email var mı diye yine aynı method userRepository'e yazılır.
        userRepository.save(User.builder()
                .username(username)
                .email(email)
                .password(password)
                .phone("")
                .isActive(true)
                .build());
        response.setData(true);
        response.setStatusCode(200);
        response.setMessage("Kullanıcı oluşturuldu");
        return response;
    }

    public Response<User> login(String userName, String password) {
        Optional<User> userOptional = userRepository.findByUsernameAndPassword(userName, password);
        Response<User> response = new Response<>();
        if (userOptional.isEmpty()) {
            response.setStatusCode(400);
            response.setMessage("Kullanıcı adı ya da sifre hatalı");
            response.setData(null);
            return response;
        }
        response.setStatusCode(200);
        response.setMessage("Giris yapildi");
        response.setData(userOptional.get());
        return response;
    }
}
