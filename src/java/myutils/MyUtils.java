/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Random;

/**
 *
 * @author pkimanh03
 */
public class MyUtils {
    public static String encrypt(String password) throws NoSuchAlgorithmException {
        String result = "";
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashedPassword = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        StringBuilder hexString = new StringBuilder(2 * hashedPassword.length);
        for (int i = 0; i < hashedPassword.length; i++) {
            String hex = Integer.toHexString(0xff & hashedPassword[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        result = hexString.toString();
        return result;
    }
    
    public static HashSet<Integer> randomUniqueNumber(int quantity, int maxValue) {
        Random rand = new Random();
        int e;
        int i;
        int g = quantity;
        HashSet<Integer> randomNumbers = new HashSet<Integer>();

        for (i = 0; i < g; i++) {
            e = rand.nextInt(maxValue);
            randomNumbers.add(e);
            if (randomNumbers.size() <= quantity) {
                if (randomNumbers.size() == quantity) {
                    g = quantity;
                }
                g++;
                randomNumbers.add(e);
            }
        }
        return randomNumbers;
    }
}
