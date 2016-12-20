package com.oneapm.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.oneapm.user.entity.OneAPMPrivateKey;
import com.oneapm.user.entity.OneAPMPubKey;
import com.oneapm.user.exception.IllegalEncryptedTextException;
import com.oneapm.user.request.EncryptedRequest;
import com.oneapm.user.service.KeyService;
import com.oneapm.user.utils.RSA;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class KeyServiceImpl implements KeyService {

    private ConcurrentHashMap<String, OneAPMPrivateKey> keyStore = new ConcurrentHashMap<>();

    @Override
    @SneakyThrows
    public OneAPMPubKey getKey() {
        OneAPMPrivateKey privateKey = RSA.keyRSA();
        keyStore.put(privateKey.getId(), privateKey);
        return privateKey.transformToPubKey();
    }

    @Override
    public <T> Optional<T> decrypt(EncryptedRequest request, Class<T> clazz) throws IllegalEncryptedTextException {
        try {
            OneAPMPrivateKey key = keyStore.get(request.getKeyId());
            if (key != null) {
                PrivateKey privateKey = RSA.generateRSAPrivateKey(key);
                return Optional.ofNullable(JSON.parseObject(RSA.decrypt(privateKey, request.getSecretText()), clazz));
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalEncryptedTextException("解密失败", e);
        }
    }
}
