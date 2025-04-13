package com.example.intern_manegement_app;

import java.util.HashMap;
import java.util.Map;

public class DataPreprocessor {

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

    public static void main(String[] args) {
        // Example input
        String input1 = """
                intern_id : 101,
                university : Cairo University,
                name : Ahmed Achoure,
                theme_id : 1,
                phone_number : +1123456789,
                IS_ACCEPTED : Accepted,
                age : 30,
                email : ahmed.sayed@example.com,
                start_date : 2023-03-01 00:00:00
                """;

        String input2 = """
                department_description : Description 4,
                department_id : 4,
                department_name : Department 4,
                location : Location 4,
                fax : 555666777
                """;

        String input3 = """
                department_id : 1,
                theme_id : 1,
                salt : null,
                password_creation_date : null,
                department_id : 4,
                supervisor_id : 4,
                fax_number : null,
                full_name : ADM,
                email_address : null,
                user_id : 7,
                role_id : 5,
                password_hash : ADM,
                password_expiry_date : null,
                phone_number : null,
                username : ADM,
                theme_name : Reduction Chimique,
                description : wow,
                theme_responsible : null
                """;

        System.out.println("Processed Input 1:");
        System.out.println(preProcess(input1));

        System.out.println("\nProcessed Input 2:");
        System.out.println(preProcess(input2));

        System.out.println("\nProcessed Input 3:");
        System.out.println(preProcess(input3));
    }
}
