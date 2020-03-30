package com.starbucks.id.rest;

import android.util.Base64;

import com.starbucks.id.R;
import com.starbucks.id.helper.StarbucksID;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Angga N P on 8/24/2018.
 */
public class RestSecurityGen {
    private static final int CIPHER_KEY_LEN = 16; //128 bits

    public static String encrypt(String src) {
        try {
            IvParameterSpec ivSpec = new IvParameterSpec(StarbucksID.Companion.getInstance().getString(R.string.enc_iv).getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKey = new SecretKeySpec(fixKey(StarbucksID.Companion.getInstance().getString(R.string.enc_key)).
                    getBytes(StandardCharsets.UTF_8), StarbucksID.Companion.getInstance().getString(R.string.key_algorithm));

            Cipher cipher = Cipher.getInstance(StarbucksID.Companion.getInstance().getString(R.string.factory_algorithm));
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

            return Base64.encodeToString(cipher.doFinal(src.getBytes()), Base64.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String src) {
        String decrypted = "";

        try {
            IvParameterSpec ivSpec = new IvParameterSpec(StarbucksID.Companion.getInstance().getString(R.string.enc_iv).getBytes(StandardCharsets.UTF_8));
            SecretKeySpec secretKey = new SecretKeySpec(fixKey(StarbucksID.Companion.getInstance().getString(R.string.enc_key)).
                    getBytes(StandardCharsets.UTF_8), StarbucksID.Companion.getInstance().getString(R.string.key_algorithm));

            Cipher cipher = Cipher.getInstance(StarbucksID.Companion.getInstance().getString(R.string.factory_algorithm));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
            decrypted = new String(cipher.doFinal(Base64.decode(src, Base64.DEFAULT)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return decrypted;
    }

    private static String fixKey(String key) {

        if (key.length() < CIPHER_KEY_LEN) {
            int numPad = CIPHER_KEY_LEN - key.length();

            StringBuilder keyBuilder = new StringBuilder(key);
            for (int i = 0; i < numPad; i++) {
                keyBuilder.append("0"); //0 pad to len 16 bytes
            }
            key = keyBuilder.toString();

            return key;

        }

        if (key.length() > CIPHER_KEY_LEN) {
            return key.substring(0, CIPHER_KEY_LEN); //truncate to 16 bytes
        }

        return key;
    }
}
