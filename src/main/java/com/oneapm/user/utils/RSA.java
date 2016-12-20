package com.oneapm.user.utils;

import com.oneapm.user.entity.OneAPMPrivateKey;
import com.oneapm.user.entity.OneAPMPubKey;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static org.apache.commons.codec.binary.Base64.encodeBase64String;

public final class RSA {

    private static final String RSA_CRYPT_ALGORITHM_NAME = "RSA";

    private static final String RSA_SIGN_ALGORITHM_NAME = "MD5withRSA";

    private static final String ENCODING = "UTF-8";

    private static final int RSA_KEY_LENGTH = 1024;

    private RSA() {
        // No instance
    }

    /**
     * 生成密钥对
     *
     * @return KeyPair
     */
    @SneakyThrows
    private static KeyPair generateKeyPair() {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA_CRYPT_ALGORITHM_NAME);
        SecureRandom random = new SecureRandom();
        random.setSeed(RandomStringUtils.randomAlphanumeric(RSA_KEY_LENGTH).getBytes());
        keyPairGen.initialize(RSA_KEY_LENGTH, random);
        return keyPairGen.genKeyPair();
    }

    /**
     * 生产RSA密钥对
     */
    public static OneAPMPrivateKey keyRSA() {
        KeyPair keyPair = generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] pubKeyBytes = publicKey.getEncoded();
        byte[] priKeyBytes = privateKey.getEncoded();
        return new OneAPMPrivateKey(new OneAPMPubKey(encodeBase64String(pubKeyBytes)), encodeBase64String(priKeyBytes));
    }

    /**
     * 生成公钥
     */
    @SneakyThrows
    public static PublicKey generateRSAPublicKey(OneAPMPubKey pubKey) {
        byte[] encodedKey = Base64.decodeBase64(pubKey.getPubKey());
        X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(encodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_CRYPT_ALGORITHM_NAME);
        return keyFactory.generatePublic(bobPubKeySpec);
    }

    @SneakyThrows
    public static PrivateKey generateRSAPrivateKey(OneAPMPrivateKey privateKey) {
        byte[] encodedKey = Base64.decodeBase64(privateKey.getPrivateKey());
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(encodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_CRYPT_ALGORITHM_NAME);
        return keyFactory.generatePrivate(priPKCS8);
    }

    /**
     * 加密
     *
     * @param key  加密的密钥
     * @param data 待加密的明文数据
     * @return 加密后的数据
     */
    @SneakyThrows
    public static String encrypt(@NonNull Key key, String data) {
        Cipher cipher = Cipher.getInstance(RSA_CRYPT_ALGORITHM_NAME);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return encodeBase64String(cipher.doFinal(data.getBytes(ENCODING)));
    }

    /**
     * 解密
     *
     * @param key        解密的密钥
     */
    @SneakyThrows
    public static String decrypt(@NonNull Key key, String raw) {
        Cipher cipher = Cipher.getInstance(RSA_CRYPT_ALGORITHM_NAME);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.decodeBase64(raw)), ENCODING);
    }

    /**
     * 签名
     *
     * @param privateKey
     * @param data
     * @return
     */
    @SneakyThrows
    public static byte[] sign(PrivateKey privateKey, byte[] data) {
        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(RSA_SIGN_ALGORITHM_NAME);
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    /**
     * 验证签名是否正确
     *
     * @param publicKey
     * @param signed
     * @param orig
     * @return
     */
    @SneakyThrows
    public static boolean verify(PublicKey publicKey, byte[] signed, byte[] orig) {
        Signature signature = Signature.getInstance(RSA_SIGN_ALGORITHM_NAME);
        signature.initVerify(publicKey);
        signature.update(orig);
        return signature.verify(signed);
    }
}
