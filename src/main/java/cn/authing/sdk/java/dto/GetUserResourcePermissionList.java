package cn.authing.sdk.java.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;


public class GetUserResourcePermissionList {
    /**
     * 权限空间 code
     */
    @JsonProperty("namespaceCode")
    private String namespaceCode;
    /**
     * 数据资源权限操作列表
     */
    @JsonProperty("actions")
    private List<String> actions;
    /**
     * 资源路径
     */
    @JsonProperty("resources")
    private String resources;

    public String getNamespaceCode() {
        return namespaceCode;
    }
    public void setNamespaceCode(String namespaceCode) {
        this.namespaceCode = namespaceCode;
    }

    public List<String> getActions() {
        return actions;
    }
    public void setActions(List<String> actions) {
        this.actions = actions;
    }

    public String getResources() {
        return resources;
    }
    public void setResources(String resources) {
        this.resources = resources;
    }



}