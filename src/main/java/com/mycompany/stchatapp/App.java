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
        MessageClass messageManager = new MessageClass();

        // PART ONE: REGISTRATION
        System.out.println("===== ST Chat App - Registration =====");

        System.out.print("Enter first name: ");
        String firstName = sc.nextLine();

        System.out.print("Enter last name: ");
        String lastName = sc.nextLine();

        System.out.print("Enter username (must contain _ and "
                + "be no more than 5 characters): ");
        String username = sc.nextLine();

        System.out.print("Enter password (minimum 8 characters, "
                + "capital letter, number and special character): ");
        String password = sc.nextLine();

        System.out.print("Enter SA cellphone number (e.g. +27838968976): ");
        String cell = sc.nextLine();

        String registrationMessage = login.registerUser(
                username, password, cell, firstName, lastName);

        System.out.println("\n" + registrationMessage);

        if (!registrationMessage.startsWith("Username successfully")) {
            System.out.println("Registration unsuccessful. Goodbye!");
            sc.close();
            return;
        }

        // PART ONE: LOGIN
        System.out.println("\n===== Login =====");

        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.print("Enter username (or type exit to quit): ");
            String loginUsername = sc.nextLine();

            if (loginUsername.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                sc.close();
                return;
            }

            System.out.print("Enter password: ");
            String loginPassword = sc.nextLine();

            String status = login.returnLoginStatus(
                    loginUsername, loginPassword);

            System.out.println(status);

            if (status.startsWith("Welcome")) {
                loggedIn = true;
            } else {
                System.out.println("Please try again.\n");
            }
        }

        // PART TWO: QUICKCHAT
        System.out.println("\nWelcome to QuickChat.");

        boolean running = true;

        while (running) {
            System.out.println("\n===== QuickChat Menu =====");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");

            String option = sc.nextLine();

            switch (option) {

                case "1":
                    System.out.print(
                            "How many messages do you want to enter? ");

                    int numberOfMessages;

                    try {
                        numberOfMessages =
                                Integer.parseInt(sc.nextLine());

                        if (numberOfMessages <= 0) {
                            System.out.println(
                                    "Please enter a number greater than zero.");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Enter a valid whole number.");
                        break;
                    }

                    int completed = 0;

                    while (completed < numberOfMessages) {

                        System.out.println(
                                "\nMessage " + (completed + 1));

                        System.out.print(
                                "Enter recipient cellphone (+27...): ");
                        String recipient = sc.nextLine();

                        if (!messageManager.checkRecipientCell(recipient)) {
                            System.out.println(
                                    "Cell phone number incorrectly formatted "
                                    + "or missing the international code.");
                            continue;
                        }

                        System.out.print("Enter your message: ");
                        String messageText = sc.nextLine();

                        if (!messageManager.checkMessageLength(messageText)) {
                            System.out.println(
                                    "Message exceeds 250 characters. "
                                    + "Please reduce the size.");
                            continue;
                        }

                        if (messageText.trim().isEmpty()) {
                            System.out.println(
                                    "Message cannot be empty.");
                            continue;
                        }

                        MessageClass message =
                                new MessageClass(recipient, messageText);

                        System.out.println(
                                "Message ID: " + message.getMessageID());
                        System.out.println(
                                "Message Hash: " + message.getMessageHash());

                        System.out.println("\nChoose an action:");
                        System.out.println("1) Send Message");
                        System.out.println("2) Disregard Message");
                        System.out.println("3) Store Message");
                        System.out.print("Your choice: ");

                        int choice;

                        try {
                            choice = Integer.parseInt(sc.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid choice.");
                            continue;
                        }

                        String result = message.SentMessage(choice);
                        System.out.println(result);
// RUBRIC: Add Message: Message details display correctly[cite: 4]
                        if (choice == 1
                                && result.equals(
                                "Message successfully sent.")) {

                            System.out.println("\nMessage details:");
                            System.out.println(
                                    "Message ID: " + message.getMessageID());
                            System.out.println(
                                    "Message Hash: "
                                    + message.getMessageHash());
                            System.out.println(
                                    "Recipient: " + message.getRecipient());
                            System.out.println(
                                    "Message: " + message.getMessage());

                            completed++;
                        } else if (choice == 3
                                && result.equals(
                                "Message successfully stored.")) {
                            completed++;
                        } else if (choice == 2) {
                            completed++;
                        }
                    }

                    System.out.println("\nTotal messages sent: "
                            + messageManager.returnTotalMessages());
                    break;

                case "2":
                    System.out.println("Coming Soon.");
                    break;

                case "3":
                    System.out.println("Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println(
                            "Invalid option. Please choose 1, 2 or 3.");
            }
        }

        sc.close();
    }
}

    

        
        
    


    

