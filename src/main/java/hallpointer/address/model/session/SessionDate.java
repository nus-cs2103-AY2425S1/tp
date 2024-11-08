package hallpointer.address.model.session;

import static hallpointer.address.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a SessionDate.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class SessionDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Dates must be in the format dd MMM yyyy,\n"
                    + "although it is case-insensitive and does not need zero-padding.\n"
                    + "Example: 24 Sep 2024";

    // Desired date format
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");
    // Whitespace processing already done earlier
    public static final Pattern parsingPattern = Pattern.compile("^([0-9]{1,2}) ([a-zA-Z]{3}) ([0-9]{4})$");

    public final LocalDate fullDate;

    /**
     * Constructs a {@code SessionDate}.
     *
     * @param date A valid date string.
     */
    public SessionDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        fullDate = LocalDate.parse(manualFormatDate(date), DATE_FORMATTER);
    }

    /**
     * Parses minor errors in date formatting and fixes them.
     * Minor errors in this case referring to zero-padding and month case.
     *
     * @param date the input date in question
     * @return the correctly formatted date
     */
    private static String manualFormatDate(String date) {
        requireNonNull(date);
        Matcher matcher = parsingPattern.matcher(date);
        if (!matcher.find()) {
            return ""; // no amount of formatting can save this given the input constraints
        }
        // matcher.group(0) returns the whole string
        String day = matcher.group(1);
        String month = matcher.group(2);
        String year = matcher.group(3);

        // Fix zero-padding requirement
        if (day.length() == 1) {
            day = "0" + day;
        }
        // Fix month case issue
        month = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase();
        // Nothing can be done about year errors due to ambiguity
        return day + " " + month + " " + year;
    }

    /**
     * Returns true if a given string is a valid date and has the expected format.
     */
    public static boolean isValidDate(String date) {
        try {
            String formattedDate = manualFormatDate(date);
            LocalDate parsedDate = LocalDate.parse(formattedDate, DATE_FORMATTER);
            return parsedDate.format(DATE_FORMATTER).equals(formattedDate); // throws error if it doesn't work
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return fullDate.format(DATE_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SessionDate)) {
            return false;
        }

        SessionDate otherDate = (SessionDate) other;
        return fullDate.equals(otherDate.fullDate);
    }

    @Override
    public int hashCode() {
        return fullDate.hashCode();
    }
}
