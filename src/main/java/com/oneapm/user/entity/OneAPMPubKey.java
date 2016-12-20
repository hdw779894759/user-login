package com.oneapm.user.entity;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class OneAPMPubKey extends OneAPMKey {

    private static final long serialVersionUID = 7987664241230431372L;

    private String pubKey;

    public OneAPMPubKey(String pubKey) {
        this.pubKey = pubKey;
    }
}
