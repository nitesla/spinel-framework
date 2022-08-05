package com.spinel.framework.dto.requestDto;


import com.spinel.framework.models.RolePermission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RolePermissionDto {
    private Long id;
    private Long roleId;
    private List<RolePermission> permissionIds;
}
