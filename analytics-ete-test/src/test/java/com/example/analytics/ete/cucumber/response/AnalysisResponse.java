package com.example.analytics.ete.cucumber.response;


import java.util.Set;

public class AnalysisResponse {
    private String type;
    private Integer owner;
    private Set<Integer> viewers;
    private String hiddenInfo;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Set<Integer> getViewers() {
        return viewers;
    }

    public void setViewers(Set<Integer> viewers) {
        this.viewers = viewers;
    }

    public String getHiddenInfo() {
        return hiddenInfo;
    }

    public void setHiddenInfo(String hiddenInfo) {
        this.hiddenInfo = hiddenInfo;
    }


}

