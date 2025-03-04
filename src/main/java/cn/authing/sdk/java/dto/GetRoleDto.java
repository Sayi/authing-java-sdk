package cn.authing.sdk.java.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;


public class GetRoleDto {
    /**
     * 权限分组(权限空间)内角色的唯一标识符
     */
    @JsonProperty("code")
    private String code;
    /**
     * 所属权限分组(权限空间)的 Code
     */
    @JsonProperty("namespace")
    private String namespace;

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getNamespace() {
        return namespace;
    }
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }



}