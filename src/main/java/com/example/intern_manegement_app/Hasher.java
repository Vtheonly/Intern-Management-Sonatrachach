package com.example.intern_manegement_app;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Hasher {

    public static String hash_it(String input) throws NoSuchAlgorithmException {
        String result = input;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(result.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

    public static boolean isEqual(String Entered_hash, String hashed_pass){
        return hashed_pass.equals(Entered_hash);
    }

}
