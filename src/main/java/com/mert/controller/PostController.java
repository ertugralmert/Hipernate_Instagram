package com.mert.controller;

import com.mert.service.PostService;

import java.util.Scanner;

public class PostController {

    private final PostService postService;

    public PostController(){
        this.postService = new PostService();
    }

    /**
     * Instagram'ın gerçek çalışma süreci
     *  ->> Login (username, password) ->> uygulamaya girdğimizde tekrar sormuyor.
     *  -->> Login işlemi yapıldıktan sonra giriş yapan kullanıcıya benzersiz bir TOKEN üretilir ve iletilir.
     *  bu token genellikle (JWT) şeklinde kullanılır.
     *  -->> JWT ilgili cihazda saklanır ve işlem yapılırken bu kullanılır.
     *  --> POST atarken, yorum yazarken, like atarken kimlik doğrulama işlemi için JWT kullanılır.
     *  Jason Web TOKEN.....
     */


    public void newPost(){
        System.out.println("""
                ***************************
                ***** POST OLUŞTUR ********
                """);
        System.out.print("Kullanıcı adı: ");
        String userName = new Scanner(System.in).nextLine();
        System.out.print("Açıklama: ");
        String comment = new Scanner(System.in).nextLine();
        System.out.print("Resim: ");
        String imageUrl = new Scanner(System.in).nextLine();
        System.out.println("Adres: ");
        String location = new Scanner(System.in).nextLine();
//        postService.addNewPost(userName,comment,imageUrl,location);
    }
}
