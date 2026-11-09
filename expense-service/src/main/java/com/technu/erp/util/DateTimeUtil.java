package com.technu.erp.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtil {

    public static String getCurrentDateAndTimeInIST() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeFormat.YYYY_MM_HH_MM_SS.value);
        sdf.setTimeZone(TimeZone.getTimeZone(DateTimeZone.IST.value));
        return sdf.format(date);
    }

    public static String convertLocalDateToDD_MM_YYYY(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern(DateTimeFormat.DD_MM_YYYY.value));
    }

    public enum DateTimeFormat {
        DD_MM_YYYY_SLASH("dd/MM/yyyy"), DD_MM_YYYY("dd-MM-yyyy"), YYYY_MM_HH_MM_SS("yyyy-MM-dd HH:mm:ss");
        public final String value;

        DateTimeFormat(String value) {
            this.value = value;
        }
    }

    public enum DateTimeZone {
        IST("IST"), GMT("GMT");

        public final String value;

        DateTimeZone(String value) {
            this.value = value;
        }
    }
}
