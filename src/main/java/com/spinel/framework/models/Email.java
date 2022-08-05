package com.spinel.framework.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Email {

    private String to;
    private String subject;
    private String body;

}
