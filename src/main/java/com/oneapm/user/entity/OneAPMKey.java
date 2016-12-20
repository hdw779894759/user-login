package com.oneapm.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Duration;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
abstract class OneAPMKey implements Serializable {

    private static final long serialVersionUID = 5686110814570011909L;

    private String id = UUID.randomUUID().toString().replaceAll("-", "");

    private String encryptType = "RSA";

    private long timestamp = System.currentTimeMillis();

    private long ttl = Duration.ofMinutes(5).toMillis();
}
