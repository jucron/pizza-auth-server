package com.renault.pizzaauthserver.config;

import org.jasypt.util.text.AES256TextEncryptor;
import org.junit.jupiter.api.Test;

class DevPropertiesLoadTest {

    @Test
    public void encrypt() {
        String username = "postgres";
        String password = "password";
        //
        AES256TextEncryptor encryptor = new AES256TextEncryptor();
        encryptor.setPassword("jhsad892");
        System.out.println("username = "+encryptor.encrypt(username));
        System.out.println("password = "+encryptor.encrypt(password));
    }

    @Test
    public void decrypt() {
        String encryptedUsername = "QdW/d+la+XCjzn1QDPdoD7n9VBWAoR/Fnfz9z2bjTy4SKPA8dH6jMfiCF1/wgbF5";
        //
        AES256TextEncryptor encryptor = new AES256TextEncryptor();
        encryptor.setPassword("jhsad892");
        System.out.println("encryptedUsername = "+encryptor.decrypt(encryptedUsername));
    }

}