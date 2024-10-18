package com.vernon.dto;

public class AccessTokenDTO {
    private String corpid;
    private String corpsecret;

    public AccessTokenDTO(String corpid, String corpsecret) {
        this.corpid = corpid;
        this.corpsecret = corpsecret;
    }

    public String getCorpid() {
        return corpid;
    }

    public String getCorpsecret() {
        return corpsecret;
    }

    @Override
    public String toString() {
        return "AccessTokenDTO{" +
                "corpid='" + corpid + '\'' +
                ", corpsecret='" + corpsecret + '\'' +
                '}';
    }
}
