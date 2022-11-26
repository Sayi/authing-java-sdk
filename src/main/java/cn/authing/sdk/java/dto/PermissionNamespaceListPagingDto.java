package cn.authing.sdk.java.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

import cn.authing.sdk.java.dto.PermissionNamespaceRolesListRespDto;

public class PermissionNamespaceListPagingDto {
    /**
     * 记录总数
     */
    @JsonProperty("totalCount")
    private Integer totalCount;
    /**
     * 响应数据
     */
    @JsonProperty("list")
    private List<PermissionNamespaceRolesListRespDto> list;

    public Integer getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<PermissionNamespaceRolesListRespDto> getList() {
        return list;
    }
    public void setList(List<PermissionNamespaceRolesListRespDto> list) {
        this.list = list;
    }



}