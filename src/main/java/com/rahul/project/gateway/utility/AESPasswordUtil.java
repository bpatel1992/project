package com.rahul.project.gateway.utility;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.URLCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class AESPasswordUtil {

    private final Logger logger = LoggerFactory.getLogger(AESPasswordUtil.class);

    private byte[] raw = new byte[]{'M', 'P', '@', 'g', '0', '@', 'n', '@', 'c', '0', 'd', 'a', '2', '4', '1', '1'};

    private URLCodec urlCodec = new URLCodec("ISO-8859-1");

    public String userEncrypt(String value) {
        byte[] encrypted;
        String encryptedPass = "";
        try {

            Key skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] iv = new byte[cipher.getBlockSize()];

            IvParameterSpec ivParams = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParams);
            encrypted = cipher.doFinal(value.getBytes(StandardCharsets.ISO_8859_1));
            encryptedPass = urlCodec.encode(Base64.encodeBase64URLSafeString(encrypted));
//            logger.info("encrypted string:" + encryptedPass);

        } catch (Exception ex) {
            logger.error("Exception in userEncrypt method of AESPasswordUtil ", ex);
        }
        return encryptedPass;
    }

    public String userDecrypt(String decryptedInp) {
        byte[] original;
        Cipher cipher;
        byte[] decrypted;
        String decryptedOut = null;
        try {
            Key key = new SecretKeySpec(raw, "AES");
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] ivByte = new byte[cipher.getBlockSize()];
            IvParameterSpec ivParamsSpec = new IvParameterSpec(ivByte);
            cipher.init(Cipher.DECRYPT_MODE, key, ivParamsSpec);

            decrypted = Base64.decodeBase64(urlCodec.decode(decryptedInp).getBytes(StandardCharsets.ISO_8859_1));
            original = cipher.doFinal(decrypted);
            decryptedOut = new String(original, StandardCharsets.ISO_8859_1);

        } catch (Exception ex) {
            logger.error("Exception in userDecrypt method of AESPasswordUtil ", ex);
        }
        return decryptedOut;
    }

    public String convertToAES(String inpPass) {


        String hashedString;
        byte[] digest = null;

        String outPass = userEncrypt(inpPass);
        digest = outPass.getBytes(StandardCharsets.UTF_8);
        hashedString = toHex(digest);

        return hashedString;
    }

    private String toHex(byte[] bytes) {
        BigInteger bi = new BigInteger(1, bytes);

        return String.format("%0" + (bytes.length << 1) + "x", bi);
    }

   /* public static void main(String[] args){
        AESPasswordUtil aesPasswordUtil = new AESPasswordUtil();
        System.out.println(aesPasswordUtil.convertToAES("password"));
    }*/
}
