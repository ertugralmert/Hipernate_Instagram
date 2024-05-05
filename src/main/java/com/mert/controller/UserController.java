package com.mert.controller;

import com.mert.service.UserService;
import com.mert.utility.Response;

import java.util.Scanner;

public class UserController {

    private final UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public void register() {
        boolean isRegister = false;
        do {
            System.out.println("""
                    ***************************
                    ***** KAYIT OL ************
                    """);
            System.out.print("Kullanıcı adı: ");
            String userName = new Scanner(System.in).nextLine();
            System.out.print("Email: ");
            String email = new Scanner(System.in).nextLine();
            System.out.print("Sifre: ");
            String password = new Scanner(System.in).nextLine();
            System.out.println("Şifre doğrulama: ");
            String confirmPassword = new Scanner(System.in).nextLine();
            /**
             * 1- Eğer şifreler uyuşmuyor ise ,tekrar giriş yapması sağlanır ->> CONTROLLER
             * 2- Bilgiler zorunludur , boş bırakılamaz ve eksik girilemez.
             */
            // == referansları kontrol eder.
            if (!password.equals(confirmPassword)) {
                System.out.println("Sifreler uyusmuyor");
            } else if (userName.length() < 3 || userName.length() > 64) {
                System.out.println("Kullanıcı adı 3 ile 64 karakter arasında olmalıdır");
            } else if (!email.contains("@")) {
                System.out.println("Gecersiz email adresi");
            }else {
                Response<Boolean> response = userService.register(userName, email, password);
                isRegister = response.getData();
                System.out.println(response.getMessage()); // kullanıcıya mesaj dönecek.

            }
        } while (!isRegister);
    }

    public void login(){
        System.out.println("""
                ***************************
                ******** KAYIT OL *********
                **************************
                """);
        System.out.println("Kullanıcı Adı: ");
        String userName = new Scanner(System.in).nextLine();
        System.out.println("Sifre: ");
        String password = new Scanner(System.in).nextLine();
        Response<Boolean> response = userService.login(userName, password);
        if(response.getStatusCode()==400 || response.getStatusCode()==500){
            System.out.println(response.getMessage());
        }else{
            /**
             * Giriş başarılı oldu işlemleri yapılacak.
             * Geri dönen jullanıcı bilgisi uygulama içinde static olarak saklanacak
             * Mobil uygulamalarda local de tutulur.
             */
        }


    }
}
