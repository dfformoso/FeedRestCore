package com.denis.feed.authToken.enums;

/**
 * Created by denisfeijooformoso on 06/02/16.
 */
public enum TokenStatus {

    ACTIVE((byte) 0),
    INACTIVE((byte) 1);

    private byte status;

    TokenStatus(byte status) {
        this.status = status;
    }

    public TokenStatus getFromStatus(byte status) {
        for (TokenStatus tokenStatus : TokenStatus.values()) {
            if (tokenStatus.status == status) {
                return tokenStatus;
            }
        }
        return null;
    }

    public byte getStatus() {
        return status;
    }
}
