package com.spinel.framework.helpers;

import com.spinel.framework.exceptions.BadRequestException;
import com.spinel.framework.utils.CustomResponseCode;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateFormatter {

    public static void checkStartAndEndDate(LocalDateTime startDate, LocalDateTime endDate) {
        if((startDate != null && endDate == null) || (endDate != null && startDate == null)){
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Date cannot be empty");
        }
        if(startDate != null) {
            if (startDate.isAfter(endDate)) {
                throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "start date cannot be later than end date");
            }
        }
    }

    public static Date tryParseDate(String date) {
        Date response = null;
        try {
            response = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        }catch (Exception e){
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, e.getMessage());
        }
        return response;
    }

    public static LocalDateTime convertToLocalDate(String date) {
        Date dateToConvert = tryParseDate(date);
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

}
