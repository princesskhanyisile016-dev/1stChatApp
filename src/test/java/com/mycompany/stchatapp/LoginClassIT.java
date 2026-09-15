/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.stchatapp;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author khanyisile princess
 */
public class LoginClassIT {
    
    public LoginClassIT() {
    }

    @org.junit.Test
    public void testSomeMethod() {
    }
    



    LoginClass login = new LoginClass();

    // ========== USERNAME TESTS ==========

    @Test
    public void testCheckUserName_Correct() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testCheckUserName_Incorrect() {
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    // ========== PASSWORD COMPLEXITY TESTS ==========

    @Test
    public void testCheckPasswordComplexity_Correct() {
        assertTrue(login.checkPasswordComplexity("Ch&sec@ke99!"));
    }

    @Test
    public void testCheckPasswordComplexity_Incorrect() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    // ========== CELL PHONE TESTS ==========

    @Test
    public void testCheckCellPhoneNumber_Correct() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCheckCellPhoneNumber_Incorrect() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    // ========== REGISTER USER MESSAGE TESTS ==========

    @Test
    public void testRegisterUser_UsernameIncorrect() {
        String expected = "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        String actual = login.registerUser("kyle!!!!!!!", "Ch&sec@ke99!", "+27838968976");
        assertEquals(expected, actual);
    }

    @Test
    public void testRegisterUser_PasswordIncorrect() {
        String expected = "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        String actual = login.registerUser("kyl_1", "password", "+27838968976");
        assertEquals(expected, actual);
    }

    @Test
    public void testRegisterUser_CellIncorrect() {
        String expected = "Cell phone number incorrectly formatted or does not contain international code.";
        String actual = login.registerUser("kyl_1", "Ch&sec@ke99!", "08966553");
        assertEquals(expected, actual);
    }

    // ========== LOGIN TESTS ==========

    @Test
    public void testLoginUser_Successful() {
        // First register a valid user
        login.registerUser("kyl_1", "Ch&sec@ke99!", "+27838968976", "Kyle", "Smith");
        
        assertTrue(login.loginUser("kyl_1", "Ch&sec@ke99!"));
    }

    @Test
    public void testLoginUser_Failed() {
        login.registerUser("kyl_1", "Ch&sec@ke99!", "+27838968976", "Kyle", "Smith");
        
        assertFalse(login.loginUser("wrong", "wrongpass"));
    }

    @Test
    public void testReturnLoginStatus_Successful() {
        login.registerUser("kyl_1", "Ch&sec@ke99!", "+27838968976", "Kyle", "Smith");
        
        String expected = "Welcome Kyle, Smith it is great to see you again.";
        String actual = login.returnLoginStatus("kyl_1", "Ch&sec@ke99!");
        assertEquals(expected, actual);
    }

    @Test
    public void testReturnLoginStatus_Failed() {
        login.registerUser("kyl_1", "Ch&sec@ke99!", "+27838968976", "Kyle", "Smith");
        
        String expected = "Username or password incorrect, please try again.";
        String actual = login.returnLoginStatus("wrong", "wrongpass");
        assertEquals(expected, actual);
    }
}