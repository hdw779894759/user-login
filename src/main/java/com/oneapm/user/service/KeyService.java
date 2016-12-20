package com.oneapm.user.service;

import com.oneapm.user.entity.OneAPMPubKey;
import com.oneapm.user.exception.IllegalEncryptedTextException;
import com.oneapm.user.request.EncryptedRequest;

import java.util.Optional;

public interface KeyService {

    OneAPMPubKey getKey();

    /**
     * JSON based decrypt method
     */
    <T> Optional<T> decrypt(EncryptedRequest request, Class<T> clazz) throws IllegalEncryptedTextException;
}
