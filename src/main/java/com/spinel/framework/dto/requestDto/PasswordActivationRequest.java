package com.spinel.framework.dto.requestDto;


import lombok.Data;

@Data
public class PasswordActivationRequest {

    private Long id;
    private String password;
}
