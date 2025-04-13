package com.example.intern_manegement_app;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class toolkit {


    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int PASSWORD_LENGTH = 8;
    private static final int ASCII_START = 33;
    private static final int ASCII_END = 126;  //'!'  to '~' characters



    public static Map<String, String> parseText(String text) {
        Map<String, String> map = new HashMap<>();
        String[] lines = text.split("\n");
        for (String line : lines) {
            String[] parts = line.split(":");
            if (parts.length == 2) {
                String key = parts[0].trim();
                String value = parts[1].trim();
                // Remove leading and trailing commas if present
                if (value.endsWith(",")) {
                    value = value.substring(0, value.length() - 1);
                }
                map.put(key, value);
            }
        }
        return map;
    }


    public static String preProcess(String input) {
        Map<String, String> replacements = new HashMap<>();
        replacements.put("password_creation_date", "Password Creation Date");
        replacements.put("department_id", "Department ID");
        replacements.put("department_name", "Department Name");
        replacements.put("supervisor_id", "Supervisor Name");
        replacements.put("fax_number", "Fax Number");
        replacements.put("full_name", "Full Name");
        replacements.put("email_address", "Email Address");
        replacements.put("user_id", "User ID");
        replacements.put("role_id", "Role");
        replacements.put("password_expiry_date", "Password Expiry Date");
        replacements.put("phone_number", "Phone Number");
        replacements.put("username", "Username");
        replacements.put("theme_id", "Theme ID");
        replacements.put("theme_name", "Theme Name");
        replacements.put("description", "Description");
        replacements.put("theme_responsible", "Theme Responsible");
        replacements.put("department_description", "Department Description");
        replacements.put("location", "Location");
        replacements.put("fax", "Fax");
        replacements.put("intern_id", "Intern ID");
        replacements.put("university", "University");
        replacements.put("name", "Name");
        replacements.put("IS_ACCEPTED", "Decision");
        replacements.put("age", "Age");
        replacements.put("email", "Email");
        replacements.put("start_date", "Start Date");

        // Remove unnecessary fields like "salt" and "password_hash"
        String[] lines = input.split(",\n");
        StringBuilder result = new StringBuilder();

        for (String line : lines) {
            if (line.contains("salt") || line.contains("password_hash")) {
                // Skip this line as it contains a field to be deleted
                continue;
            }

            // Split the line into key-value pairs by the first occurrence of ":"
            String[] keyValue = line.split(":", 2);
            if (keyValue.length < 2) {
                continue;
            }

            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            // Check if the key exists in the replacements map, if so, replace it
            if (replacements.containsKey(key)) {
                key = replacements.get(key);
            }

            // Append the reformatted line to the result
            result.append(key).append(": ").append(value).append(",\n");
        }

        // Remove the last comma and newline
        if (result.length() > 0) {
            result.setLength(result.length() - 2);  // Remove ",\n"
        }

        return result.toString();
    }


    // this func was AI generated
    // it makes it ready for the view in the pane
    public static String formatString(String Info) {
        // Remove the curly braces
        Info = Info.substring(1, Info.length() - 1);

        // Split the string by ", " to get key-value pairs
        String[] pairs = Info.split(", ");

        // Use a StringBuilder to construct the result string
        StringBuilder result = new StringBuilder();

        // Iterate over the pairs and format them
        for (String pair : pairs) {
            // Split each pair by "=" to get key and value
            String[] keyValue = pair.split("=");
            String key = keyValue[0];
            String value = keyValue[1];

            // Append formatted key-value to the result
            result.append(key).append(" : ").append(value).append(",\n");
        }

        // Remove the trailing comma and newline
        if (result.length() > 0) {
            result.setLength(result.length() - 2);
        }

        return result.toString();
    }


    public static String generatePassword() {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int randomAscii = ASCII_START + RANDOM.nextInt(ASCII_END - ASCII_START + 1);
            password.append((char) randomAscii);
        }
        return password.toString();
    }

    public static String hashIt(String input) throws NoSuchAlgorithmException {
        String result = input;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(result.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

    public static boolean isHashEqual(String Entered_hash, String hashed_pass){
        return hashed_pass.equals(Entered_hash);
    }



}
