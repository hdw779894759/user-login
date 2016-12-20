package com.oneapm.user.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class EncryptedRequest implements Serializable {
    private static final long serialVersionUID = 7341239407950545625L;

    private String keyId;

    private String secretText;
}
