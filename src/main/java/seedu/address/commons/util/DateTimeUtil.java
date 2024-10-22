package seedu.address.commons.util;

import java.time.format.DateTimeFormatter;

/**
 * A class for formatting and parsing LocalDate objects
 */
public class DateTimeUtil {
    public static final DateTimeFormatter DEFAULT_DATE_PARSER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
}
