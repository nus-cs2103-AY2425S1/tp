package seedu.address.model.log;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the content of a log in the address book.
 */
public class LogEntry {
    public static final String MESSAGE_CONSTRAINTS = "Log description should not be blank.";
    public static final String VALIDATION_REGEX = ".+";
    private final String entry;

    /**
     * Constructs a {@code LogEntry}.
     *
     * @param entry A valid log entry.
     */
    public LogEntry(String entry) {
        checkArgument(isValidEntry(entry), MESSAGE_CONSTRAINTS);
        this.entry = entry;
    }

    /**
     * Returns the log entry.
     */
    public String getEntry() {
        return entry;
    }

    /**
     * Returns the truncated log entry.
     */
    public String getTruncatedEntry() {
        return entry.length() > 100
                ? entry.substring(0, 100) + "..."
                : entry;
    }

    /**
     * Returns true if a given string is a valid entry.
     */
    public static boolean isValidEntry(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LogEntry // instanceof handles nulls
                && entry.equals(((LogEntry) other).entry)); // state check
    }

    @Override
    public int hashCode() {
        return entry.hashCode();
    }
}
