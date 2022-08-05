package com.spinel.framework.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.Date;




@EqualsAndHashCode(callSuper=false)
@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends CoreEntity{


//    private Long loginAttempts;
    private int loginAttempts;
    private LocalDateTime failedLoginDate;
    private LocalDateTime lastLogin;
    private String password;
    private String passwordExpiration;
    private Date lockedDate;
    private String firstName;
    private String lastName;
    private String middleName;
    private String username;
    private Long roleId;
    private String role;
    @Transient
    private String roleName;
    private LocalDateTime passwordChangedOn;
    private String cardBin;
    private String cardLast4;
    @Transient
    private boolean loginStatus;
    private String email;
    private String phone;
    // TODO validate not less than 4 digits and not more than 6 digits
    private String transactionPin;
    private String transactionPinStatus;
    private String userCategory;
    private String resetToken;
    private String resetTokenExpirationDate;
    private Long clientId;
    private Long wareHouseId;
    @Lob
    private String photo;
    private Long globalAdminUserId;
    private Long countryId;
    private String country;

// --------logistic  userType -----------

    @Transient
    private String userType;



}
