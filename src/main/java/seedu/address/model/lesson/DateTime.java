package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.DateTimeUtil.dateTimeToString;
import static seedu.address.commons.util.DateTimeUtil.isValidDateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class DateTime {
    private final LocalDateTime dateTime;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
            .withLocale(Locale.getDefault());
    private static final String MESSAGE_CONSTRAINTS = "DateTime must be in the format of " + formatter;

    /**
     * Constructs a {@code DateTime}.
     *
     * @param dateTime A valid date and time.
     */
    public DateTime(LocalDateTime dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidDateTime(dateTimeToString(dateTime)), MESSAGE_CONSTRAINTS);
        this.dateTime = dateTime;
    }

    /**
     * Returns the date and time.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Returns true if a given date time is before the other.
     *
     * @param other The other date time to compare with.
     * @return True if this date time is before the other.
     */
    public boolean isBefore(DateTime other) {
        return this.dateTime.isBefore(other.getDateTime());
    }

    /**
     * Returns true if a given date time is after the other.
     *
     * @param other The other date time to compare with.
     * @return True if this date time is after the other.
     */
    public boolean isAfter(DateTime other) {
        return this.dateTime.isAfter(other.getDateTime());
    }

    @Override
    public String toString() {
        return dateTime.format(formatter);
    }
}
