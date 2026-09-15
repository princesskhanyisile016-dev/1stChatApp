/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.stchatapp;
import java.util.regex.Pattern;

/**
 *
 * @author khanyisile princess
 */
public class LoginClass {
    
    // Stored registration details (simple in-memory storage for this console app)
    private String registeredUsername;
    private String registeredPassword;
    private String registeredCellNumber;
    private String firstName;
    private String lastName;

    // ---------- Validation methods required by the PoE ----------

    /**
     * Username must contain an underscore (_) and be no longer than 5 characters.
     */
    public boolean checkUserName(String username) {
        if (username == null) return false;
        return username.contains("_") && username.length() <= 5;
    }

    /**
     * Password complexity rules:
     * - at least 8 characters
     * - at least one capital letter
     * - at least one number
     * - at least one special character
     */
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) return false;

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasCapital = true;
            else if (Character.isDigit(c)) hasNumber = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }
        return hasCapital && hasNumber && hasSpecial;
    }

    /**
     * Cell-phone number must start with the international code (+27)
     * and the total length of the number part after +27 must be ≤ 10 digits.
     * Regular-expression based checker (as required).
     *
     * Example of a valid SA number: +27838968976
     */
    public boolean checkCellPhoneNumber(String cellNumber) {
        if (cellNumber == null) return false;

        // Must start with +27 and then 1–10 digits
        String regex = "^\\+27\\d{1,10}$";
        return Pattern.matches(regex, cellNumber);
    }

    // ---------- Registration messaging ----------

    /**
     * Returns the appropriate registration message based on the three checks.
     */
    public String registerUser(String username, String password, String cellNumber) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellNumber)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        // All good – store the details
        this.registeredUsername = username;
        this.registeredPassword = password;
        this.registeredCellNumber = cellNumber;

        return "Username successfully captured.\nPassword successfully captured.\nCell phone number successfully added.";
    }

    // Convenience overload that also stores first & last name (used by the welcome message)
    public String registerUser(String username, String password, String cellNumber,
                               String firstName, String lastName) {
        String result = registerUser(username, password, cellNumber);
        if (result.contains("successfully")) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
        return result;
    }

    // ---------- Login methods ----------

    /**
     * Checks whether the supplied credentials match the registered ones.
     */
    public boolean loginUser(String username, String password) {
        if (registeredUsername == null || registeredPassword == null) {
            return false;
        }
        return registeredUsername.equals(username) && registeredPassword.equals(password);
    }

    /**
     * Returns the welcome / failure message required by the PoE.
     */
    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // Optional getters (useful for unit tests / debugging)
    public String getRegisteredUsername() { return registeredUsername; }
    public String getRegisteredPassword() { return registeredPassword; }
    public String getRegisteredCellNumber() { return registeredCellNumber; }
}


