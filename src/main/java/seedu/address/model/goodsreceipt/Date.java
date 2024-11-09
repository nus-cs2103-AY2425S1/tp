package seedu.address.model.goodsreceipt;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * Represents a date related to goods.
 */
public class Date {
    private static final String DATE_FORMAT_READ = "uuuu-MM-dd HH:mm";
    private static final String DATE_FORMAT_WRITE = "EEEE : dd MMMM yyyy, hh.mm a";
    public static final String MESSAGE_INVALID_FORMAT =
            String.format("Dates should only be in the format of <%s>.", DATE_FORMAT_READ);
    private static final DateTimeFormatter PATTERN_READ = DateTimeFormatter.ofPattern(DATE_FORMAT_READ)
            .withResolverStyle(ResolverStyle.STRICT);
    private static final DateTimeFormatter PATTERN_DISPLAY = DateTimeFormatter.ofPattern(DATE_FORMAT_WRITE)
            .withZone(ZoneId.of("Singapore"));

    private LocalDateTime dateTime;

    /**
     * Constructs Date object for Goods.
     */
    public Date(String dateTime) {
        requireNonNull(dateTime);
        this.dateTime = LocalDateTime.parse(dateTime, PATTERN_READ);
    }

    /**
     * Returns the datetime.
     */
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    /**
     * Checks if this date is strictly before another date.
     */
    public boolean isBefore(Date otherDate) {
        // Check for date equality first
        if (this.dateTime.equals(otherDate.dateTime)) {
            return true;
        }

        return this.dateTime.isBefore(otherDate.dateTime);
    }

    /**
     * Returns the date time string, formatted for user display.
     */
    public String getReadableDateTimeString() {
        return dateTime.format(PATTERN_DISPLAY);
    }

    /**
     * Checks if the current date has passed.
     * Considers the dateTime in real-time.
     */
    public boolean hasPassed() {
        return this.dateTime.isBefore(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return dateTime.format(PATTERN_READ);
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
