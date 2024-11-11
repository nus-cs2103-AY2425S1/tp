package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.logging.Logger;

/**
 * Represents a meeting date in the meeting book.
 * Guarantees: immutable; meeting date is valid as declared in {@link #isValidMeetingDate(String)}.
 */
public class MeetingDate {
    public static final String MESSAGE_CONSTRAINTS =
            "Meeting dates need to be in the format dd-MM-yyyy and must be a valid date. "
                    + "The date must be today or in the future.";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-uuuu")
            .withResolverStyle(ResolverStyle.STRICT);
    private static final Logger logger = Logger.getLogger(MeetingDate.class.getName());
    public final String value;
    /**
     * Constructs a {@code MeetingDate}.
     *
     * @param meetingDate A valid meeting date.
     */
    public MeetingDate(String meetingDate) {
        requireNonNull(meetingDate);
        checkArgument(isValidMeetingDate(meetingDate), MESSAGE_CONSTRAINTS);
        value = meetingDate;
        logger.info("MeetingDate created: " + this);
    }

    /**
     * Returns true if a given string is a valid meeting date in the format dd-MM-yyyy.
     */
    public static boolean isValidMeetingDate(String test) {
        try {
            LocalDate date = LocalDate.parse(test, DATE_FORMATTER);
            return !date.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MeetingDate)) {
            return false;
        }

        MeetingDate otherMeetingDate = (MeetingDate) other;
        return value.equals(otherMeetingDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
