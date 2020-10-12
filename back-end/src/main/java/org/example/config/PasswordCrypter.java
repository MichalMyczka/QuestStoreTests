package org.example.config;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordCrypter {

    public static String crypter(String originalString) {
        String sha256hex = DigestUtils.sha256Hex(originalString);
        return sha256hex;
    }

}
