package com.vernon.dto;

import java.util.List;

public class SetScopeDTO {

    private int agentId;

    List<String> allowUser;

    List<Integer> allowParty;

    List<Integer> allowTag;

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public List<String> getAllowUser() {
        return allowUser;
    }

    public void setAllowUser(List<String> allowUser) {
        this.allowUser = allowUser;
    }

    public List<Integer> getAllowParty() {
        return allowParty;
    }

    public void setAllowParty(List<Integer> allowParty) {
        this.allowParty = allowParty;
    }

    public List<Integer> getAllowTag() {
        return allowTag;
    }

    public void setAllowTag(List<Integer> allowTag) {
        this.allowTag = allowTag;
    }

    @Override
    public String toString() {
        return "SetScopeDTO{" +
                "agentId=" + agentId +
                ", allowUser=" + allowUser +
                ", allowParty=" + allowParty +
                ", allowTag=" + allowTag +
                '}';
    }
}
