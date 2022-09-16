package com.spinel.framework.service;


import com.spinel.framework.helpers.API;
import com.spinel.framework.notification.requestDto.NotificationRequestDto;
import com.spinel.framework.notification.requestDto.RecipientRequest;
import com.spinel.framework.notification.requestDto.SmsRequest;
import com.spinel.framework.notification.requestDto.VoiceOtpRequest;
import com.spinel.framework.notification.responseDto.NotificationResponseDto;
import com.spinel.framework.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


@SuppressWarnings("ALL")
@Slf4j
@Service
public class NotificationService {

    private String mailFrom;

    @Value("${mail.sender}")
    private String mailSender;

    @Value("${mail.hostName}")
    private String mailHostName;

    @Value("${mail.smtpPort}")
    private String mailSmtpPort;

    @Value("${mail.username}")
    private String mailUsername;

    @Value("${mail.password}")
    private String mailPassword;

    @Value("${mail.subject}")
    private String subject;

    @Value("${space.sms.url}")
    private String smsNotification;

    @Value("${space.notification.url}")
    private String multipleNotification;

    @Value("${authKey.notification}")
    private String authKey;

    @Value("${phoneNo.notification}")
    private String phoneNo;

    @Value("${notification.unique.id}")
    private String uniqueId;

    @Value("${voice.otp.url}")
    private String voiceOtp;

    static final String CONFIGSET = "ConfigSet";


    @Autowired
    ExternalTokenService externalTokenService;

    @Autowired
    private API api;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ModelMapper mapper;

    public NotificationService( ModelMapper mapper) {
        this.mapper = mapper;
    }






    public void emailNotificationRequest1 (NotificationRequestDto notificationRequestDto){


        Map<String,String> map = new HashMap();
        map.put("auth-key", authKey);
        map.put("fingerprint", uniqueId);

        notificationRequestDto.setEmail(true);
        notificationRequestDto.setInApp(true);
        notificationRequestDto.setMessage(notificationRequestDto.getMessage());
        notificationRequestDto.getRecipients().forEach(p -> {
            RecipientRequest tran = RecipientRequest.builder()
                    .email(p.getEmail())
                    .build();
            p.setPhoneNo(phoneNo);
        });
        notificationRequestDto.setSms(false);
        notificationRequestDto.setTitle(Constants.NOTIFICATION);
        NotificationResponseDto response = api.post(multipleNotification, notificationRequestDto, NotificationResponseDto.class, map);


    }



    public void smsNotificationRequest (SmsRequest smsRequest){
        Map<String,String> map = new HashMap();
        map.put("fingerprint", uniqueId);
         api.postNotification(smsNotification, smsRequest,  map);


    }



    public void voiceOtp (VoiceOtpRequest voiceOtpRequest){
        Map<String,String> map = new HashMap();
        map.put("fingerprint", uniqueId);
        api.postNotification(voiceOtp, voiceOtpRequest, map);

    }

    public void emailNotificationRequest(NotificationRequestDto notificationRequestDto) {

        try {
            Properties props = System.getProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.port", mailSmtpPort);
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");

            // Create a Session object to represent a mail session with the specified properties.
            Session session = Session.getDefaultInstance(props);

            // Create a message with the specified information.
            MimeMessage msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(mailFrom, mailSender));

            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(notificationRequestDto.getRecipient()));


            msg.setSubject(notificationRequestDto.getTitle());
            msg.setContent(notificationRequestDto.getMessage(), "text/html");

            // Add a configuration set header. Comment or delete the
            // next line if you are not using a configuration set
            msg.setHeader("X-SES-CONFIGURATION-SET", CONFIGSET);

            // Create a transport.
            Transport transport = session.getTransport();

            // Send the message.
            try {
                System.out.println("Sending...");

                // Connect to Amazon SES using the SMTP username and password you specified above.
                transport.connect(mailHostName, mailUsername, mailPassword);

                // Send the email.
                transport.sendMessage(msg, msg.getAllRecipients());
                System.out.println("Email sent!");
            } catch (Exception ex) {
                System.out.println("The email was not sent.");
                System.out.println("Error message: " + ex.getMessage());
            } finally {
                // Close and terminate the connection.
                transport.close();
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
