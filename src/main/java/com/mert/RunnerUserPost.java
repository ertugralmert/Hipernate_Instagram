package com.mert;

import com.mert.controller.UserController;

import java.util.Scanner;

public class RunnerUserPost {

    public static void main(String[] args) {
        UserController userController = new UserController();
        int secim;
        do {
            System.out.println("""
                    ***************
                    ---- Login ---
                    1- Login
                    2- Register
                    3- Exit
                    """);
            System.out.println("seçin: ");
            secim = new Scanner(System.in).nextInt();
            switch (secim) {
                case 1:

                    break;
                case 2:
                    new UserController().register();
                    break;
                case 3:
                    System.out.println("Çıkış yapılıyor");
                    break;
                default:
                    System.out.println("Yanlış secim");

            }
        } while (secim != 0);

    }

}
