package cn.authing.sdk.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ListApplicationsDto {
    /**
     * 当前页数，从 1 开始
     */
    @JsonProperty("page")
    private Integer page;
    /**
     * 每页数目，最大不能超过 50，默认为 10
     */
    @JsonProperty("limit")
    private Integer limit;
    /**
     * 是否为集成应用
     */
    @JsonProperty("isIntegrateApp")
    private Boolean isIntegrateApp;
    /**
     * 是否为自建应用
     */
    @JsonProperty("isSelfBuiltApp")
    private Boolean isSelfBuiltApp;
    /**
     * 是否开启单点登录
     */
    @JsonProperty("ssoEnabled")
    private Boolean ssoEnabled;
    /**
     * 模糊搜索字符串
     */
    @JsonProperty("keywords")
    private String keywords;
    /**
     * 搜索应用，true：搜索所有应用, 默认为 false
     */
    @JsonProperty("all")
    private Boolean all;

    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Boolean getIsIntegrateApp() {
        return isIntegrateApp;
    }
    public void setIsIntegrateApp(Boolean isIntegrateApp) {
        this.isIntegrateApp = isIntegrateApp;
    }

    public Boolean getIsSelfBuiltApp() {
        return isSelfBuiltApp;
    }
    public void setIsSelfBuiltApp(Boolean isSelfBuiltApp) {
        this.isSelfBuiltApp = isSelfBuiltApp;
    }

    public Boolean getSsoEnabled() {
        return ssoEnabled;
    }
    public void setSsoEnabled(Boolean ssoEnabled) {
        this.ssoEnabled = ssoEnabled;
    }

    public String getKeywords() {
        return keywords;
    }
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Boolean getAll() {
        return all;
    }
    public void setAll(Boolean all) {
        this.all = all;
    }



}