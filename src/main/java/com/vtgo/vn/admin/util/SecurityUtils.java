/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vtgo.vn.admin.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author HP
 */
public class SecurityUtils {

    private static byte[] key = {'V', 'T', 'G', 'O', 'A', 'P', 'P', 'T', 'O',
        'A', 'N', 'N', 'V', 'K', 'E', 'Y'};

    private static final Logger logger = Logger.getLogger(SecurityUtils.class);

    public static String createToken(String str, Date date) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((str + String.valueOf(date.getTime())).getBytes("UTF-8"));
            return Hex.encodeHexString(hash);
        } catch (Exception e) {
            logger.error(e, e);
            return null;
        }
    }

    /**
     * Hàm mã hóa mật khẩu sử dụng SHA-256 và salt
     *
     * @param password
     * @param salt
     * @return
     */
    public static String hashPassword(String password, String salt) {
        String passwordAndSalt = password + salt;
        try {
            return encrypt(passwordAndSalt);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            return "";
        }
    }

    /**
     * Hàm mã hóa mật khẩu sử dụng SHA-256
     *
     * @param plaintext
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    public static String encrypt(String plaintext)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256"); // step 2
        md.update(plaintext.getBytes("UTF-8")); // step 3
        byte raw[] = md.digest(); // step 4
        String hash = Base64.encodeBase64String(raw); // step 5
        return hash; // step 6

        // Hoac
        // MessageDigest digest = MessageDigest.getInstance("SHA-256");
        // byte[] hash = digest.digest((plaintext).getBytes("UTF-8"));
        // return Hex.encodeHexString(hash);
        // Hoac
        // byte[] hash = digest.digest(plaintext.getBytes());
        // return Base64.encodeBase64String(hash);
    }

    public static String genRandomPassword() {

        // A - Z: 65 -> 90
        // a-z: 97 - > 122
        // 0 - 9: 48 - 57
        // ki tu dac biet: 33-47
        // ki tu dac biet: 33,64,35,36,37,94,38,42 (!@#$%^&*)
        // @: 64
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            String result = "";
            ArrayList<Integer> cards = new ArrayList<>();
            Integer[] pass = new Integer[8];
            for (int i = 0; i < 8; i++) {
                if (i < 3) { // Random 3 ki tu so
                    pass[i] = random.nextInt(10) + 48;
                } else if (i < 6) { // Random 3 ki tu chu cai thuong
                    pass[i] = random.nextInt(26) + 97;
                } else if (i == 6) { // Random chu hoa
                    pass[i] = random.nextInt(26) + 65;
                } else { // Random ki tu dac biet
                    int[] arrSc = new int[]{33, 64, 35, 36, 37, 94, 38, 42};
                    int arrScLength = arrSc.length;
                    int sc = random.nextInt(arrScLength);
                    pass[i] = arrSc[sc];
                }
            }
            cards.addAll(Arrays.asList(pass));
            Collections.shuffle(cards);
            for (Integer i : cards) {
                result = result + (char) (i.intValue());
            }

            return result;
        } catch (NullPointerException | NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
            return "123456aA@";
        }
    }

    public static String createSalt() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        SecureRandom random = null;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
        }
        if (random != null) {
            for (int i = 0; i < 10; i++) {
                char c = chars[random.nextInt(chars.length)];
                sb.append(c);
            }
            String output = sb.toString();
            return output;
        } else {
            return "vtgosalt";
        }
    }
}
