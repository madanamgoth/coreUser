package com.example.CoreUser.Util;


import java.security.SecureRandom;

public class AppUtil {

    public static String getID(String data) {
        try {
            String ts = String.valueOf(System.currentTimeMillis());
            SecureRandom random = new SecureRandom();
            long number = random.nextLong(1_000_000_000L, 10_000_000_000L);
            return data + ts + "." + number;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
