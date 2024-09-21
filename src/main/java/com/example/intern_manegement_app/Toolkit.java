package com.example.intern_manegement_app;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class Toolkit {


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


    // this func was AI generated
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




}
