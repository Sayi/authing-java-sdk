package cn.authing.sdk.java.dto.authentication;

import java.util.Map;

public class CasingUserInfo {

    private String service;
    private String id;
    private Map<String, Object> attributes;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
