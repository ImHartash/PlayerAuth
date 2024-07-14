package me.imhartash.playerauth.Utils;

import me.imhartash.playerauth.PlayerAuth;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Encryptor {

    private final static String DEFAULT_KEY = "E9DWxkF3fQgeWFBj";
    private final static String DEFAULT_IV = "TujCywe5XCdJ87KS";

    private static String KEY_FLAG = PlayerAuth.plugin.getConfig().getString("key_flag");
    private static String IV_FLAG = PlayerAuth.plugin.getConfig().getString("iv_flag");

    public static void setup() {
        if (KEY_FLAG.equals("")) {
            KEY_FLAG = DEFAULT_KEY;
        }
        if (IV_FLAG.equals("")) {
            IV_FLAG = DEFAULT_IV;
        }
    }

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
