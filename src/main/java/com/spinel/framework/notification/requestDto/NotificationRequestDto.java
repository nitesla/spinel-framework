package com.spinel.framework.notification.requestDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *
 * This class collects the request and map it to the entity class
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NotificationRequestDto {

    private Boolean email;
    private Boolean inApp;
    private String message;
    private List<RecipientRequest> recipients;
    private String recipient;
    private Boolean sms;
    private String title;



}
