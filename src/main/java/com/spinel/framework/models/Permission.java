package com.spinel.framework.models;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Transient;


@EqualsAndHashCode(callSuper=false)
@Data
@Entity
public class Permission extends CoreEntity{

    private String name;
    private String menuName;
    private String url;
    private String permissionType;

    @Transient
    private String accessList;

    public Permission(String name, String menuName, String url, String permissionType) {
        this.name = name;
        this.menuName = menuName;
        this.url = url;
        this.permissionType = permissionType;
    }
}
