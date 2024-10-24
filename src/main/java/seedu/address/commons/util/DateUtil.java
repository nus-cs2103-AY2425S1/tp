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

    private static final String DATE_FORMAT = "uuuu-MM-dd";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT)
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

    private static Optional<LocalDate> parseDate(String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date, DATE_FORMATTER);
            logger.fine("Parsed date: " + parsedDate);
            return Optional.of(parsedDate);
        } catch (DateTimeException e) {
            logger.warning("Unable to parse date: " + date);
            return Optional.empty();
        }
    }

}
