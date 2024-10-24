package seedu.address.model.goodsreceipt;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Represents a date related to goods.
 */
public class Date {
    private static final String DATE_FORMAT_READ = "yyyy-MM-dd HH:mm";
    private static final String DATE_FORMAT_WRITE = "EEEE : dd MMMM yyyy, hh.mm a";
    public static final String MESSAGE_INVALID_FORMAT =
            String.format("Dates should only be in the format of <%s>.", DATE_FORMAT_READ);
    private static final DateTimeFormatter PATTERN_READ = DateTimeFormatter.ofPattern(DATE_FORMAT_READ);
    private static final DateTimeFormatter PATTERN_WRITE = DateTimeFormatter.ofPattern(DATE_FORMAT_WRITE)
            .withZone(ZoneId.of("Singapore"));

    private LocalDateTime dateTime;

    /**
     * Constructs Date object for Goods.
     */
    public Date(String dateTime) {
        requireNonNull(dateTime);
        // Basic check for the length of string format
        checkArgument(dateTime.length() > 10);
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
        return dateTime.format(PATTERN_WRITE);
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
