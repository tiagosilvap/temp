package com.hotmart.api.subscription.checkouttokens3.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class HashBuilder {

    private static final String AES_KEY = "YTk3OTM4Y2IxN2U2MjI2NQ==";

    private static final String ALGORITHM = "AES";

    public static String decrypt(String string) throws Exception {
        Cipher aes = Cipher.getInstance(HashBuilder.ALGORITHM);
        aes.init(Cipher.DECRYPT_MODE, HashBuilder.getKey());

        return new String(aes.doFinal(Hex.decodeHex(string.toCharArray())));
    }

    public static String encrypt(String toEncrypt) throws Exception {
        Cipher aes = Cipher.getInstance(HashBuilder.ALGORITHM);
        aes.init(Cipher.ENCRYPT_MODE, HashBuilder.getKey());

        return new String(Hex.encodeHex((aes.doFinal(toEncrypt.getBytes()))));
    }

    private static SecretKeySpec getKey() {
        byte[] key64 = Base64.decodeBase64(HashBuilder.AES_KEY);
        return new SecretKeySpec(key64, HashBuilder.ALGORITHM);
    }
}
