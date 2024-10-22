package seedu.address.commons.core.dateformatter;

import java.time.format.DateTimeFormatter;

/**
 * A class to store the available DateFormatters used by Prudy.
 */
public class DateFormatter {
    public static final String MM_DD_YYYY_PATTERN = "MM/dd/yyyy";
    public static final DateTimeFormatter MM_DD_YYYY_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    // Insert more formatters here
}
