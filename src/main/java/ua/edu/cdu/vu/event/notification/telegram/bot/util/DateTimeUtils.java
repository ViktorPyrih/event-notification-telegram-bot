package ua.edu.cdu.vu.event.notification.telegram.bot.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@UtilityClass
public class DateTimeUtils {

    private static final int DATE_ONLY_LENGTH = 10;
    private static final String REQUIRED_DATE_TIME_PART = "T00";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy['T'HH[:mm[:ss]]]");

    public static LocalDateTime parse(String text) {
        String dateTime = text;
        if (Objects.requireNonNull(text).length() == DATE_ONLY_LENGTH) {
            dateTime = text.concat(REQUIRED_DATE_TIME_PART);
        }
        return LocalDateTime.parse(dateTime, FORMATTER);
    }

    public static String format(LocalDateTime dateTime) {
        return Objects.requireNonNull(dateTime).format(FORMATTER);
    }
}
