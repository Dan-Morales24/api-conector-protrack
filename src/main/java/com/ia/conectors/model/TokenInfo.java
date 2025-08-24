package com.ia.conectors.model;


public class TokenInfo {
    public String token;
    public long expiry; 

    public TokenInfo(String token, long expiry) {
        this.token = token;
        this.expiry = expiry;
    }

    public String getToken() {
        return token;
    }

    public long getExpiry() {
        return expiry;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }
}

