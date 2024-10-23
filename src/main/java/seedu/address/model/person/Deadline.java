package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a Person's deadline in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {

    public static final String MESSAGE_CONSTRAINTS =
            "Deadlines should be in the format dd-MM-yyyy, and it should be a valid date.";
    public static final String NO_DEADLINE = "__No_Deadline__";
    public static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy");

    private static final Logger logger = Logger.getLogger(Deadline.class.getName());

    public final LocalDate value;

    /**
     * Constructs a {@code Deadline}.
     *
     * @param deadline A valid deadline in dd-MM-yyyy format.
     */
    public Deadline(String deadline) {
        requireNonNull(deadline);
        if (deadline.equals(NO_DEADLINE)) {
            this.value = LocalDate.MIN;
        } else {
            checkArgument(isValidDeadline(deadline), MESSAGE_CONSTRAINTS);
            this.value = LocalDate.parse(deadline, INPUT_FORMATTER);
        }
    }

    /**
     * Returns true if a given string is a valid deadline.
     */
    public static boolean isValidDeadline(String test) {
        try {
            // Parse the string into a LocalDate and check if the string matches the expected format
            LocalDate parsedDate = LocalDate.parse(test, INPUT_FORMATTER);

            // Necessary to prevent dates like "31-02-2020" being accepted (they get converted to "28-02-2020")
            String reformattedDate = parsedDate.format(INPUT_FORMATTER);
            return reformattedDate.equals(test);
        } catch (DateTimeParseException e) {
            logger.log(Level.WARNING, "Invalid deadline format: {0}", test);
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
        return value.format(OUTPUT_FORMATTER);
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
