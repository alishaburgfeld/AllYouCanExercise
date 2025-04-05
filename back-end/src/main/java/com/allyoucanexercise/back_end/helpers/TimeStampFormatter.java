package com.allyoucanexercise.back_end.helpers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeStampFormatter {

    // Convert Timestamp to formatted String
    public static String formatTimestamp(Timestamp timestamp) {
        if (timestamp == null)
            return "";
        LocalDateTime localDateTime = timestamp.toLocalDateTime();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dtf.format(localDateTime);
    }

    // Convert LocalDateTime to Timestamp
    public static Timestamp toTimestamp(LocalDateTime localDateTime) {
        return localDateTime == null ? null : Timestamp.valueOf(localDateTime);
    }

    // Convert Timestamp to LocalDateTime
    public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }

    // Convert String time to TimeStamp
    public static Timestamp stringToTimestamp(String time) {
        // example string: "2024-12-31 10:30:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime fixedDateTime = LocalDateTime.parse(time, formatter);

        return Timestamp.valueOf(fixedDateTime);
    }
}