package dto;

import java.util.List;


public class ListChildrenDepartmentsReqDto {
    /**
     * 部门 ID
     */
    private String departmentId;
    /**
     * 组织 code
     */
    private String organizationCode;

    public String getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }
    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }



}