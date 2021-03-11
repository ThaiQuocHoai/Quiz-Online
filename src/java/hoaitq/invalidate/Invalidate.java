/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaitq.invalidate;

import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 *
 * @author QH
 */
public class Invalidate implements Serializable {

    private static final String PATTERM = "^[a-zA-Z][\\w]+@([\\w]+\\.[\\w]+|[\\w]+\\.[\\w]{2,}\\.[\\w]{2,})$";
    private static final String FULLNAME = "^[a-zA-z ]+$";

    public static boolean checkEmail(String email) {
        return Pattern.matches(PATTERM, email);
    }

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA  
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash) {
        BigInteger number = new BigInteger(1, hash);
        StringBuilder hexString = new StringBuilder(number.toString(16));
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

    public static boolean checkFullname(String fullname) {
        if (Pattern.matches(FULLNAME, fullname)) {
            return true;
        }
        return false;
    }

    public static boolean checkConfirmNotMatch(String confirm, String password) {
        if (!confirm.equals(password)) {
            return false;
        }
        return true;
    }

    public static boolean checkAnswer(String answer1, String answer2, String answer3, String answer4) {
        if (answer1.isEmpty() || answer2.isEmpty() || answer3.isEmpty() || answer4.isEmpty()) {
            return false;
        }
        return true;
    }

    public static String checkDupAnswer(String answer1, String answer2, String answer3, String answer4) {
        String[] arr = {answer1, answer2, answer3, answer4};
        String[] arr1 = {answer1, answer2, answer3, answer4};
        for (int i = 0; i < arr.length - 1; i++) {
            int flag = 0;
            for (int j = 0; j < arr1.length - 1; j++) {
                if (arr[i].equals(arr1[j])) {
                    flag++;
                }
            }
            if (flag > 1) {
                return "Answer duplicate";
            }
        }
        return "";
    }

}
