/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.stchatapp;

import java.util.Scanner;

/**
 *
 * @author khanyisile princess
 */
public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LoginClass login = new LoginClass();

        System.out.println("===== ST Chat App – Registration =====");

        System.out.print("Enter first name: ");
        String firstName = sc.nextLine();

        System.out.print("Enter last name: ");
        String lastName = sc.nextLine();

        System.out.print("Enter username (must contain _ and ≤ 5 characters): ");
        String username = sc.nextLine();

        System.out.print("Enter password (min 8 chars, 1 capital, 1 number, 1 special): ");
        String password = sc.nextLine();

        System.out.print("Enter SA cell number (e.g. +27838968976): ");
        String cell = sc.nextLine();

        // Register
        String regMsg = login.registerUser(username, password, cell, firstName, lastName);
        System.out.println("\n" + regMsg);

        // Only proceed to login if registration was successful
        if (regMsg.contains("successfully")) {
            System.out.println("\n===== Login =====");
            System.out.print("Enter username: ");
            String loginUser = sc.nextLine();
            System.out.print("Enter password: ");
            String loginPass = sc.nextLine();

            System.out.println(login.returnLoginStatus(loginUser, loginPass));
        }

        sc.close();
    }
}

    

