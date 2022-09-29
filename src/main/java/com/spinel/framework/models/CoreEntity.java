package com.spinel.framework.models;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@ToString
@MappedSuperclass
public abstract class CoreEntity implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(hidden = true)
    private LocalDate createdDate = LocalDate.now();

    @UpdateTimestamp
    @ApiModelProperty(hidden = true)
    private LocalDate updatedDate = LocalDate.now();

    private Long createdBy;
    private Long updatedBy;

    private Boolean isActive;


}
