package com.spinel.framework.dto.requestDto;


import lombok.Data;

@Data
public class CreateTransactionPinDto {

    private Long id;
    private String transactionPin;
    private String password;
    private String resetToken;
}
