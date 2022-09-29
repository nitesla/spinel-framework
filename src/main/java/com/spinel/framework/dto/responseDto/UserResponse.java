package com.spinel.framework.dto.responseDto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;



@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phone;
    private String username;
    private String photo;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private Long createdBy;
    private Long updatedBy;
    private Boolean isActive;
    private LocalDateTime failedLoginDate;
    private LocalDateTime lastLogin;
    private Long roleId;
    private String roleName;
}
