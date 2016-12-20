package com.oneapm.user.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

@Data
@EqualsAndHashCode(callSuper = true)
public class OneAPMPrivateKey extends OneAPMPubKey {

    private static final long serialVersionUID = -369498672622835471L;

    private String privateKey;

    public OneAPMPrivateKey(OneAPMPubKey pubKey, String privateKey) {
        super.setPubKey(pubKey.getPubKey());
        super.setEncryptType(pubKey.getEncryptType());
        super.setId(pubKey.getId());
        super.setTimestamp(pubKey.getTimestamp());
        super.setTtl(pubKey.getTtl());
        this.privateKey = privateKey;
    }

    public OneAPMPubKey transformToPubKey() {
        OneAPMPubKey pubKey = new OneAPMPubKey();
        BeanUtils.copyProperties(this, pubKey);
        return pubKey;
    }
}
