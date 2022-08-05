package com.spinel.framework.globaladminintegration.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SingleResponseData {

    private Long id;

    private String name;

    private String code;

    private Long countryId;

    private String countryName;

    private long stateId;
    private long lgaId;

    private String stateName;

    private String lgaName;

    private String permissionType;
    private String appPermission;
    private String menuName;

    private String createdDate;

    private String updatedDate;

    private Long createdBy;

    private Long updatedBy;
}
