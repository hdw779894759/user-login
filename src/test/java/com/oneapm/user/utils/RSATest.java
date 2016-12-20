package com.oneapm.user.utils;

import com.oneapm.user.entity.OneAPMPrivateKey;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RSATest {

    @Test
    public void testRSA() throws Exception {
        OneAPMPrivateKey keyPair = RSA.keyRSA();
        PublicKey publicKey = RSA.generateRSAPublicKey(keyPair.transformToPubKey());
        PrivateKey privateKey = RSA.generateRSAPrivateKey(keyPair);

        String rawStr = RandomStringUtils.randomAlphabetic(100);

        String secretText = RSA.encrypt(publicKey, rawStr);
        String result = RSA.decrypt(privateKey, secretText);

        assertThat(rawStr, is(result));
    }
}
