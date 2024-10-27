package seedu.address.commons.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Helper functions for handling dates.
 */
public class DateUtil {

    private static final String DATE_VALIDATION_FORMAT = "uuuu-MM-dd";
    private static final String DATE_FORMAT_PATTERN = "\\d{4}-\\d{2}-\\d{2}";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_VALIDATION_FORMAT)
            .withResolverStyle(ResolverStyle.STRICT);
    private static final Logger logger = LogsCenter.getLogger(DateUtil.class);

    /**
     * Returns if a given string is a valid date in the correct format.
     */
    public static boolean isValidDate(String date) {
        return parseDate(date).isPresent();
    }

    /**
     * Returns if a given string is a valid date in the correct format and is after today.
     */
    public static boolean isDateAfterToday(String date) {
        LocalDate today = LocalDate.now();
        return parseDate(date)
                .map(parsedDate -> parsedDate.isAfter(today))
                .orElse(false);
    }

    /**
     * Returns if a given string is in the correct date format.
     */
    public static boolean isCorrectDateFormat(String date) {
        return date.matches(DATE_FORMAT_PATTERN);
    }

    private static Optional<LocalDate> parseDate(String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date, DATE_FORMATTER);
            logger.fine("Parsed date: " + parsedDate);
            return Optional.of(parsedDate);
        } catch (DateTimeException e) {
            System.out.println(e.getMessage());
            logger.warning("Unable to parse date: " + date);
            return Optional.empty();
        }
    }

}
