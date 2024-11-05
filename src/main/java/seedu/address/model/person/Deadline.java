package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.parser.ParserUtil;

/**
 * Represents a Person's deadline in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {

    public static final String MESSAGE_CONSTRAINTS =
            "Deadlines must be in one of the following formats: dd-mm-[yy]yy or dd/mm/[yy]yy or dd|mm|[yy]yy"
            + "\nThe deadline provided must also be a valid date (ex: there is no 31st February)"
            + "\nExamples of accepted deadlines: 10-10-2025, 1-5-24, 3|10|2023, 4/3/27, 01-10-28";
    public static final DateTimeFormatter FORMAT_JSON_STORAGE = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final Pattern PATTERN_DATE =
            Pattern.compile("^(\\d+)[_\\-\\/|](\\d+)[_\\-\\/|](\\d+)$");
    public static final DateTimeFormatter FORMAT_GUI_OUTPUT = DateTimeFormatter.ofPattern("MMM d, yyyy");
    public static final String DEADLINE_KEY = "deadline";
    private static final Logger logger = Logger.getLogger(Deadline.class.getName());

    public final LocalDate value;

    /**
     * Constructs a {@code Deadline}.
     *
     * @param deadline A valid deadline in dd-MM-yyyy format.
     */
    public Deadline(String deadline) {
        requireNonNull(deadline);
        checkArgument(isValidDeadline(deadline), MESSAGE_CONSTRAINTS);
        this.value = LocalDate.of(
                ParserUtil.getYear(deadline), ParserUtil.getMonth(deadline), ParserUtil.getDay(deadline));
    }

    /**
     * Returns true if a given string is a valid deadline.
     */
    public static boolean isValidDeadline(String test) {
        Matcher datetimeMatcher = PATTERN_DATE.matcher(test);

        if (datetimeMatcher.find()) {
            int day = ParserUtil.getDay(test);
            int month = ParserUtil.getMonth(test);
            int year = ParserUtil.getYear(test);

            if (year == 0) {
                return false;
            }

            try {
                // LocalDate::of method handles invalid dates like 31st Feb or -3rd of the 13th month
                LocalDate.of(year, month, day);
                return true;
            } catch (DateTimeException dte) {
                logger.log(Level.WARNING, "Invalid date provided: {0}", test);
                return false;
            }
        } else {
            logger.log(Level.WARNING, "Invalid date format: {0}", test);
            return false;
        }
    }

    /**
     * Returns true if the deadline has passed
     */
    public boolean isOverdue() {
        return value.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        return value.format(FORMAT_GUI_OUTPUT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Deadline)) {
            return false;
        }

        Deadline otherDeadline = (Deadline) other;
        return value.equals(otherDeadline.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
