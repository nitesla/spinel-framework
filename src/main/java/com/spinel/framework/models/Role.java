package com.spinel.framework.models;



import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Transient;


@EqualsAndHashCode(callSuper=false)
@Data
@Entity
public class Role extends CoreEntity{


    private String name;
    private String description;
    private Long clientId;

    @Transient
    private String users;

    public Role() {
    }

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
