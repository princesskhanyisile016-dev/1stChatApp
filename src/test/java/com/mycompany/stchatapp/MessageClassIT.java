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
public class MessageClassIT {
    
    public MessageClassIT() {
    }

    @Test
    public void testCheckMessageID() {
        MessageClass msg =
                new MessageClass("+27838968976", "Hello there");

        assertTrue(msg.checkMessageID());
        assertTrue(msg.checkMessageID("1234567890"));
        assertFalse(msg.checkMessageID("12345678901"));
    }

    @Test
    public void testCheckRecipientCellCorrect() {
        MessageClass msg =
                new MessageClass("+27838968976", "Hello there");

        assertTrue(msg.checkRecipientCell());
        assertTrue(msg.checkRecipientCell("+27838968976"));
    }

    @Test
    public void testCheckRecipientCellIncorrect() {
        MessageClass msg =
                new MessageClass("08966553", "Hello there");

        assertFalse(msg.checkRecipientCell());
        assertFalse(msg.checkRecipientCell("08966553"));
    }

    @Test
    public void testMessageLengthCorrect() {
        MessageClass msg =
                new MessageClass("+27838968976", "Hello there");

        assertTrue(msg.checkMessageLength());
    }

    @Test
    public void testMessageLengthIncorrect() {
        String longMessage = "a".repeat(251);

        MessageClass msg =
                new MessageClass("+27838968976", longMessage);

        assertFalse(msg.checkMessageLength());
    }

    @Test
    public void testSendMessage() {
        MessageClass msg =
                new MessageClass("+27838968976", "Hello there");

        assertEquals(
                "Message successfully sent.",
                msg.SentMessage(1));
    }

    @Test
    public void testDisregardMessage() {
        MessageClass msg =
                new MessageClass("+27838968976", "Hello there");

        assertEquals(
                "Press 0 to delete the message.",
                msg.SentMessage(2));
    }

    @Test
    public void testStoreMessage() {
        MessageClass msg =
                new MessageClass("+27838968976", "Hello there");

        assertEquals(
                "Message successfully stored.",
                msg.SentMessage(3));
    }
}
    
    
    
    
    
    
    



