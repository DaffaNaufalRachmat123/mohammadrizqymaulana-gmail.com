package com.starbucks.id.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.starbucks.id.R;

import java.nio.charset.StandardCharsets;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * Created by Angga N P on 5/9/2018.
 */


public class UserDefault {

    private static UserDefault mInstance;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private int iterationCount = 200;
    private Cipher ecipher;
    private Cipher dcipher;
    private String language;
    private int otpFlag;



    public UserDefault(Context context) {
        this.context = context;

        this.pref = context.getSharedPreferences(context.getString(R.string.pref_name), 0);
        this.editor = pref.edit();
    }

    public static synchronized UserDefault getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new UserDefault(context);
        }
        return mInstance;
    }

    //    APP SETTING
    /*Basic Setting*/
    public void setFirst_launch(boolean first_launch) {
        editor.putBoolean(context.getString(R.string.first_launch), first_launch);
        editor.apply();
    }

    public boolean firstLaunch() {
        return pref.getBoolean(context.getString(R.string.first_launch), true);
    }

    public void setIDLanguage(boolean language) {
        editor.putBoolean(context.getString(R.string.lang), language);
        editor.apply();
    }

    public boolean IDLanguage() {
        return pref.getBoolean(context.getString(R.string.lang), false);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setLogin(boolean login) {
        editor.putBoolean(context.getString(R.string.login_session), login);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(context.getString(R.string.login_session), false);
    }

    public boolean isPn() {
        return pref.getBoolean(context.getString(R.string.pn), false);
    }

    public void setPn(boolean login) {
        editor.putBoolean(context.getString(R.string.pn), login);
        editor.apply();
    }

    public void setStp(boolean stp) {
        editor.putBoolean(context.getString(R.string.stp), stp);
        editor.apply();
    }

    public boolean stp() {
        return pref.getBoolean(context.getString(R.string.stp), false);
    }

//
//    public void setSalt(String s){
//        editor.putString(SALT, (s.replace("=", "")));
//        editor.apply();
//    }
//
//    public String getSalt(){
//        return (pref.getString(SALT, null));
//    }



    /*****************************/

    /*Advance Data*/
    public boolean isPasscodeB() {
        return pref.getBoolean(context.getString(R.string.passcode_b), false);
    }

    public void setPasscodeB(boolean passcodeB) {
        editor.putBoolean(context.getString(R.string.passcode_b), passcodeB);
        editor.apply();
    }


    public String getPasscode() {
        return decrypt(pref.getString(context.getString(R.string.passcode), ""));
    }

    public void setPasscode(String passcode) {
        editor.putString(context.getString(R.string.passcode), encrypt(passcode));
        editor.apply();
    }


    public String getEmail() {
        return decrypt(pref.getString(context.getString(R.string.email), ""));
    }

    public void setEmail(String email) {
        editor.putString(context.getString(R.string.email), encrypt(email));
        editor.apply();
    }

    public String getAccToken() {
        return decrypt(pref.getString(context.getString(R.string.acc_token), "0"));
    }

    public void setAccToken(String token) {
        editor.putString(context.getString(R.string.acc_token), encrypt(token));
        editor.apply();
    }

    public String getRefreshToken() {
        return decrypt(pref.getString(context.getString(R.string.refresh_token), "0"));
    }

    public void setRefreshToken(String token) {
        editor.putString(context.getString(R.string.refresh_token), encrypt(token));
        editor.apply();
    }

    /*********************/

    public void clean() {
        editor.clear();
        editor.apply();
    }

    public void logOut() {

        //Static Setting
        editor.remove(context.getString(R.string.login_session));
        editor.remove(context.getString(R.string.stp));
        editor.remove(context.getString(R.string.pn));

        //Dinamic Setting
        editor.remove(context.getString(R.string.passcode_b));
        editor.remove(context.getString(R.string.passcode));
        editor.remove(context.getString(R.string.email));
        editor.remove(context.getString(R.string.acc_token));
        editor.remove(context.getString(R.string.refresh_token));

        editor.apply();
    }


    //Dinamic Encryption
    private String encrypt(String str) {
        byte[] salt = Base64.decode(context.getString(R.string.local_salt), Base64.DEFAULT);

        if (dcipher == null || ecipher == null) {
            try {
                // Create the key
                KeySpec keySpec = new PBEKeySpec(
                        context.getString(R.string.local_enc).toCharArray(),
                        Base64.decode(context.getString(R.string.local_salt), Base64.DEFAULT),
                        iterationCount);

                SecretKey key = SecretKeyFactory.getInstance(
                        context.getString(R.string.fc_algorithm)).generateSecret(keySpec);

                ecipher = Cipher.getInstance(key.getAlgorithm());
                dcipher = Cipher.getInstance(key.getAlgorithm());

                // Prepare the parameter to the ciphers
                AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

                ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
                dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes(StandardCharsets.UTF_8);

            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);

            // Encode bytes to base64 to get a string
            return Base64.encodeToString(enc, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Dinamic Decryption
    public String decrypt(String str) {
        byte[] salt = Base64.decode(context.getString(R.string.local_salt), Base64.DEFAULT);

        if (dcipher == null || ecipher == null) {
            try {
                // Create the key
                KeySpec keySpec = new PBEKeySpec(
                        context.getString(R.string.local_enc).toCharArray(),
                        Base64.decode(context.getString(R.string.local_salt), Base64.DEFAULT),
                        iterationCount);

                SecretKey key = SecretKeyFactory.getInstance(
                        context.getString(R.string.fc_algorithm)).generateSecret(keySpec);

                ecipher = Cipher.getInstance(key.getAlgorithm());
                dcipher = Cipher.getInstance(key.getAlgorithm());

                // Prepare the parameter to the ciphers
                AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

                ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
                dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            // Decode base64 to get bytes
            byte[] dec = Base64.decode(str, Base64.DEFAULT);

            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);

            // Decode using utf-8
            return new String(utf8, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getOtpFlag() {
        return otpFlag;
    }

    public void setOtpFlag(int otpFlag) {
        this.otpFlag = otpFlag;
    }

}
