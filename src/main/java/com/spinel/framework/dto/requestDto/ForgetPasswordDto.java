package com.spinel.framework.dto.requestDto;

import lombok.Data;

@Data
public class ForgetPasswordDto {

    private String email;
    private String phone;
}
