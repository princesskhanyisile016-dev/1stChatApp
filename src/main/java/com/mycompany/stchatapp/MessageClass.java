/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.stchatapp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author khanyisile princess
 */
public class MessageClass {

    private String messageID;
    private String recipient;
    private String message;
    private String messageHash;
    private String sendMessageStatus;

    private static int totalMessagesSent = 0;
    private static int messageCounter = 0;

    // Required Arrays / Collections for Part 3[cite: 3]
    private static final List<MessageClass> sentMessages = new ArrayList<>();
    private static final List<MessageClass> disregardedMessages = new ArrayList<>();
    private static final List<MessageClass> storedMessages = new ArrayList<>();
    private static final List<String> messageHashes = new ArrayList<>();
    private static final List<String> messageIDs = new ArrayList<>();

    public MessageClass() {
    }

    public MessageClass(String recipient, String message) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
        
        if (this.messageID != null) messageIDs.add(this.messageID);
        if (this.messageHash != null) messageHashes.add(this.messageHash);
    }

    private String generateMessageID() {
        long number = 1_000_000_000L + new Random().nextLong(9_000_000_000L);
        return String.valueOf(number);
    }

    public boolean checkMessageID() {
        return messageID != null && messageID.matches("\\d{1,10}");
    }

    public boolean checkMessageID(String id) {
        return id != null && id.matches("\\d{1,10}");
    }

    public boolean checkRecipientCell() {
        return checkRecipientCell(recipient);
    }

    public boolean checkRecipientCell(String cellNumber) {
        return cellNumber != null && cellNumber.matches("\\+27\\d{9}");
    }

    public boolean checkMessageLength() {
        return checkMessageLength(message);
    }

    public boolean checkMessageLength(String text) {
        return text != null && text.length() <= 250;
    }

    public String createMessageHash() {
        if (messageID == null || message == null || message.trim().isEmpty()) {
            return "";
        }
        String[] words = message.trim().split("\\s+");
        String firstTwoDigits = messageID.substring(0, 2);
        String firstWord = words[0];
        String lastWord = words[words.length - 1];

        return (firstTwoDigits + ":" + (messageCounter + 1) + ":" + firstWord + lastWord).toUpperCase();
    }

    public String SentMessage(int choice) {
        switch (choice) {
            case 1: // Send Message
                if (!checkMessageID() || !checkRecipientCell() || !checkMessageLength() || message.trim().isEmpty()) {
                    sendMessageStatus = "Message could not be sent.";
                    return sendMessageStatus;
                }
                messageCounter++;
                totalMessagesSent++;
                messageHash = createMessageHashForCounter(messageCounter);
                if (!messageHashes.contains(messageHash)) {
                    messageHashes.add(messageHash);
                }

                sendMessageStatus = "Message successfully sent.";
                sentMessages.add(this);
                return sendMessageStatus;

            case 2: // Disregard Message
                disregardedMessages.add(this);
                sendMessageStatus = "Press 0 to delete the message.";
                return sendMessageStatus;

            case 3: // Store Message
                try {
                    storeMessage();
                    storedMessages.add(this);
                    sendMessageStatus = "Message successfully stored.";
                } catch (IOException e) {
                    sendMessageStatus = "Message could not be stored.";
                }
                return sendMessageStatus;

            default:
                sendMessageStatus = "Invalid message option.";
                return sendMessageStatus;
        }
    }

    private String createMessageHashForCounter(int number) {
        String[] words = message.trim().split("\\s+");
        return (messageID.substring(0, 2) + ":" + number + ":" + words[0] + words[words.length - 1]).toUpperCase();
    }

    public String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent yet.";
        }

        StringBuilder output = new StringBuilder();
        for (MessageClass msg : sentMessages) {
            output.append("Message ID: ").append(msg.messageID)
                  .append("\nMessage Hash: ").append(msg.messageHash)
                  .append("\nRecipient: ").append(msg.recipient)
                  .append("\nMessage: ").append(msg.message)
                  .append("\n\n");
        }
        return output.toString();
    }

    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    public void storeMessage() throws IOException {
        Path path = Path.of("messages.json");
        String existing = "[]";

        if (Files.exists(path)) {
            existing = Files.readString(path).trim();
        }

        if (existing.isEmpty()) {
            existing = "[]";
        }

        if (!existing.startsWith("[") || !existing.endsWith("]")) {
            throw new IOException("Invalid JSON file format.");
        }

        String contents = existing.substring(1, existing.length() - 1).trim();
        StringBuilder updated = new StringBuilder("[");

        if (!contents.isEmpty()) {
            updated.append(contents).append(",");
        }

        updated.append("\n").append(jsonObject()).append("\n]");
        Files.writeString(path, updated.toString());
    }

    // --- Part 3 Features[cite: 3] ---
    
    // a. Display sender and recipient of all stored messages[cite: 3]
    public String displayStoredSendersAndRecipients() {
        if (storedMessages.isEmpty()) {
            return "No stored messages available.";
        }
        StringBuilder sb = new StringBuilder("Stored Messages (Recipients):\n");
        for (MessageClass m : storedMessages) {
            sb.append("Recipient: ").append(m.recipient).append("\n");
        }
        return sb.toString();
    }

    // b. Display the longest stored message[cite: 3]
    public String getLongestStoredMessage() {
        if (storedMessages.isEmpty() && sentMessages.isEmpty()) {
            return "No messages available.";
        }
        List<MessageClass> all = new ArrayList<>(storedMessages);
        all.addAll(sentMessages);

        MessageClass longest = all.get(0);
        for (MessageClass m : all) {
            if (m.message.length() > longest.message.length()) {
                longest = m;
            }
        }
        return longest.message;
    }

    // c. Search for a message ID and display corresponding recipient and message[cite: 3]
    public String searchMessageByID(String targetID) {
        List<MessageClass> all = new ArrayList<>(storedMessages);
        all.addAll(sentMessages);
        all.addAll(disregardedMessages);

        for (MessageClass m : all) {
            if (m.messageID.equals(targetID)) {
                return "Recipient: " + m.recipient + "\nMessage: " + m.message;
            }
        }
        return "Message ID not found.";
    }

    // d. Search for all messages stored for a particular recipient[cite: 3]
    public String searchMessagesByRecipient(String targetRecipient) {
        StringBuilder sb = new StringBuilder();
        List<MessageClass> all = new ArrayList<>(storedMessages);
        all.addAll(sentMessages);

        for (MessageClass m : all) {
            if (m.recipient.equals(targetRecipient)) {
                sb.append(m.message).append(" ");
            }
        }
        return sb.toString().trim();
    }

    // e. Delete a message using the message hash[cite: 3]
    public String deleteMessageByHash(String targetHash) {
        boolean removed = storedMessages.removeIf(m -> m.messageHash.equalsIgnoreCase(targetHash))
                        | sentMessages.removeIf(m -> m.messageHash.equalsIgnoreCase(targetHash))
                        | disregardedMessages.removeIf(m -> m.messageHash.equalsIgnoreCase(targetHash));

        if (removed) {
            messageHashes.remove(targetHash.toUpperCase());
            return "Message successfully deleted.";
        }
        return "Message hash not found.";
    }

    // f. Display a report that lists full details of all stored messages[cite: 3]
    public String displayStoredMessagesReport() {
        if (storedMessages.isEmpty()) {
            return "No stored messages to report.";
        }
        StringBuilder sb = new StringBuilder("===== Stored Messages Report =====\n");
        for (MessageClass m : storedMessages) {
            sb.append("Message ID: ").append(m.messageID)
              .append("\nMessage Hash: ").append(m.messageHash)
              .append("\nRecipient: ").append(m.recipient)
              .append("\nMessage: ").append(m.message)
              .append("\n---------------------------\n");
        }
        return sb.toString();
    }

    private String jsonObject() {
        return "  {\n"
                + "    \"messageID\": \"" + escape(messageID) + "\",\n"
                + "    \"recipient\": \"" + escape(recipient) + "\",\n"
                + "    \"message\": \"" + escape(message) + "\",\n"
                + "    \"messageHash\": \"" + escape(messageHash) + "\"\n"
                + "  }";
    }

    private String escape(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    public String getMessageID() { return messageID; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public String getMessageHash() { return messageHash; }
    public String getSendMessageStatus() { return sendMessageStatus; }
}