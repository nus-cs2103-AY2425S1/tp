package seedu.address.model.goods;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a date related to goods.
 */
public class Date {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String MESSAGE_INVALID_FORMAT =
            String.format("Dates should only be in the format of <%s>.", DATE_FORMAT);
    private static final DateTimeFormatter PATTERN_READ = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private LocalDateTime dateTime;

    public Date(String dateTime) {
        this.dateTime = LocalDateTime.parse(dateTime, PATTERN_READ);
    }

    /**
     * Returns the datetime.
     */
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    @Override
    public String toString() {
        return dateTime.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return dateTime.equals(otherDate.dateTime);
    }
}
