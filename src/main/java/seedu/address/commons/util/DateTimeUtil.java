package seedu.address.commons.util;

import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * A class for formatting and parsing LocalDate objects
 */
public class DateTimeUtil {
    public static final DateTimeFormatter DEFAULT_DATE_PARSER = DateTimeFormatter.ofPattern("uuuu-MM-dd")
            .withResolverStyle(ResolverStyle.STRICT);
    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
    public static final DateTimeFormatter DEFAULT_MONTH_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM")
            .withResolverStyle(ResolverStyle.STRICT);

}
