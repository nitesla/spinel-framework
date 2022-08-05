package com.spinel.framework.dto.responseDto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spinel.framework.models.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccessTokenWithUserDetails implements Serializable{


    @JsonProperty("accessToken")
    private String accessToken;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    private String email;

    @JsonProperty("menu")
    private String menu;


//    @JsonProperty("roleId")
//    private long roleId;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("middleName")
    private String middleName;


    @JsonProperty("lastLogin")
    private LocalDateTime lastLogin;



    @JsonProperty("tokenExpiry")
    private long tokenExpiry;

    @JsonProperty("userId")
    private long userId;


    @JsonProperty("clientId")
    private String clientId;

    @JsonProperty("transactionPinStatus")
    private String transactionPinStatus;

    @JsonProperty("referralCode")
    private String referralCode;

    @JsonProperty("isEmailVerified")
    private String isEmailVerified ;

    @JsonProperty("photo")
    private String photo;







    public AccessTokenWithUserDetails(String token, User user, String menu, long tokenExpiry, String clientId,
                                      String referralCode, String isEmailVerified) {
        this.accessToken = token;

        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
//        this.roleId = user.getRoleId();
        this.middleName= user.getMiddleName();
        this.lastLogin = user.getLastLogin();
        this.photo = user.getPhoto();
        this.menu = menu;
        this.tokenExpiry = tokenExpiry;
        this.userId=user.getId();
        this.clientId=clientId;
        this.transactionPinStatus=user.getTransactionPinStatus();
        this.referralCode=referralCode;
        this.isEmailVerified=isEmailVerified;







    }

}
