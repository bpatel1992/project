package com.rahul.project.gateway.hash;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.URLCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class AESSecurity {

    private final Logger logger = LoggerFactory.getLogger(AESSecurity.class);
    @Autowired
    Environment environment;
    private URLCodec urlCodec = new URLCodec("ISO-8859-1");

    public String encrypt(String value) {
        byte[] encrypted;
        String encryptedPass = "";
        try {

            Key skeySpec = new SecretKeySpec(environment.getRequiredProperty("merchant.secret.key").getBytes(), "AES");
//            Key skeySpec = new SecretKeySpec(raw, "AES");
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

}
