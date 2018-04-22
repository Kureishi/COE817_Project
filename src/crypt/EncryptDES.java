package crypt;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.security.*;
import java.util.*;
import javax.crypto.*;
import java.io.UnsupportedEncodingException;


/**
 *
 *  Encrypt and decrypt using DES algorithm
 * 
 * @author Barry
 */
public class EncryptDES {
   
    public static SecretKey desKeyGen() throws NoSuchAlgorithmException {

        // object to create keys
        KeyGenerator keygen = KeyGenerator.getInstance("DES");
        // generate key
        SecretKey DESkey = keygen.generateKey();

        return DESkey;
    }

    public static byte[] encryptDES(String message, SecretKey key) {

        try {

            Cipher desObject;
            desObject = Cipher.getInstance("DES/ECB/PKCS5Padding");

            //convert message to byte array
            byte[] messageBytes = message.getBytes();

            //init cipher encrpyt mode with Generated key
            desObject.init(Cipher.ENCRYPT_MODE, key);

            byte[] bytesEncrypted = desObject.doFinal(messageBytes);
            return bytesEncrypted;

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {

            System.out.println(e);
            return null;
        }

    }

    public static String decryptDES(byte[] encryptedMessage, SecretKey key) {

        try {
            Cipher desObject;
            desObject = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // init and execute Decypher mode with same key generated above
            desObject.init(Cipher.DECRYPT_MODE, key);
            byte[] bytesDecrypted = desObject.doFinal(encryptedMessage);

            // convert byte to string
            String outputText = new String(bytesDecrypted, "UTF-8");
            return outputText;

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {

            System.out.println(e);
            return null;
        }

    }

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        /**
         * byte nounce = 1; byte nounceTwo = (byte) (nounce + 1); SecretKey
         * newKey = desKeyGen(); byte[] encryptedMessage = encryptDES(message,
         * newKey); String output = decryptDES(encryptedMessage, newKey);
         *
         * String StringEncrypted = new String(encryptedMessage, "UTF-8");
         *
         */
    }

}
