package me.imhartash.playerauth.Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Encryptor {

    private static String KEY_FLAG = "E9DWxkF3fQgeWFBj";
    private static String IV_FLAG = "TujCywe5XCdJ87KS";



    public static String encryptString(String source) {

        try {

            IvParameterSpec iv = new IvParameterSpec(IV_FLAG.getBytes("UTF-8"));
            SecretKeySpec key = new SecretKeySpec(KEY_FLAG.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, key, iv);

            byte[] encrypted = cipher.doFinal(Base64.getEncoder().encode(source.getBytes()));
            return Base64.getEncoder().encodeToString(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String decryptText(String source) {

        try {

            IvParameterSpec iv = new IvParameterSpec(IV_FLAG.getBytes("UTF-8"));
            SecretKeySpec key = new SecretKeySpec(KEY_FLAG.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, key, iv);

            byte[] originalText = cipher.doFinal(Base64.getDecoder().decode(source.getBytes()));
            originalText = Base64.getDecoder().decode(originalText);

            return new String(originalText);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
