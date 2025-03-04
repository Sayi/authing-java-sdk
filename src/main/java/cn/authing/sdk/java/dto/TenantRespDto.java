package cn.authing.sdk.java.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;


public class TenantRespDto {
    /**
     * 租户 ID
     */
    @JsonProperty("tenantId")
    private String tenantId;
    /**
     * 用户池 ID
     */
    @JsonProperty("userPoolId")
    private String userPoolId;
    /**
     * 租户名
     */
    @JsonProperty("name")
    private String name;
    /**
     * 租户描述
     */
    @JsonProperty("description")
    private String description;
    /**
     * 租户 logo
     */
    @JsonProperty("logo")
    private List<String> logo;
    /**
     * 用户被租户拒绝登录时显示的提示文案
     */
    @JsonProperty("rejectHint")
    private String rejectHint;
    /**
     * 租户关联的应用 ID
     */
    @JsonProperty("appIds")
    private List<String> appIds;
    /**
     * 创建者用户的 ID
     */
    @JsonProperty("creator")
    private String creator;
    /**
     * 租户来源的应用 ID，该值不存在时代表租户来源为 Authing 控制台
     */
    @JsonProperty("sourceAppId")
    private String sourceAppId;
    /**
     * 创建者基本信息
     */
    @JsonProperty("creatorDetail")
    private Object creatorDetail;
    /**
     * 来源 app 基本信息
     */
    @JsonProperty("sourceAppDetail")
    private Object sourceAppDetail;

    public String getTenantId() {
        return tenantId;
    }
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUserPoolId() {
        return userPoolId;
    }
    public void setUserPoolId(String userPoolId) {
        this.userPoolId = userPoolId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getLogo() {
        return logo;
    }
    public void setLogo(List<String> logo) {
        this.logo = logo;
    }

    public String getRejectHint() {
        return rejectHint;
    }
    public void setRejectHint(String rejectHint) {
        this.rejectHint = rejectHint;
    }

    public List<String> getAppIds() {
        return appIds;
    }
    public void setAppIds(List<String> appIds) {
        this.appIds = appIds;
    }

    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getSourceAppId() {
        return sourceAppId;
    }
    public void setSourceAppId(String sourceAppId) {
        this.sourceAppId = sourceAppId;
    }

    public Object getCreatorDetail() {
        return creatorDetail;
    }
    public void setCreatorDetail(Object creatorDetail) {
        this.creatorDetail = creatorDetail;
    }

    public Object getSourceAppDetail() {
        return sourceAppDetail;
    }
    public void setSourceAppDetail(Object sourceAppDetail) {
        this.sourceAppDetail = sourceAppDetail;
    }



}