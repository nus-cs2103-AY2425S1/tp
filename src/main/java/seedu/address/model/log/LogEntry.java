package seedu.address.model.log;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the content of a log in the address book.
 */
public class LogEntry {
    public static final String MESSAGE_CONSTRAINTS = "Log description should not be blank.";
    // Validation regex represents a string that contains only new line or whitespace characters
    public static final String VALIDATION_REGEX = "^(\\\\n|\\s)*$";
    private final String entry;
    private final String formattedEntry;

    /**
     * Constructs a {@code LogEntry}.
     *
     * @param entry A valid log entry.
     */
    public LogEntry(String entry) {
        // This MUST be safe to be added into storage
        checkArgument(isValidEntry(entry), MESSAGE_CONSTRAINTS);
        this.formattedEntry = convertToFormattedString(entry);
        this.entry = entry;
    }

    /**
     * Constructs a {@code LogEntry}.
     *
     * @param formattedEntry Contains special next line that should be passed around in storageEntry form
     */
    public LogEntry formattedLogEntry(String formattedEntry) {
        return new LogEntry(convertToStorageString(formattedEntry));
    }


    /**
     * Returns the log entry.
     */
    public String getEntry() {
        return entry;
    }

    /**
     * Returns the formatted log entry.
     */
    public String getFormattedEntry() {
        return formattedEntry;
    }

    /**
     * Converts the log entry to a storage string. It is also safe to be passed a argument to any command.
     */
    public static String convertToStorageString(String formattedEntry) {
        // Replaces all tags with special characters to prevent conflicts with the parser
        // \n is converted into NEWLINE, before converting back to counter edge case where users
        // types a new line character within the replacement character for tags.
        // Eg: #i\n$ will not be converted properly.
        return formattedEntry.replace("\n", "{NEWLINE}")
                .replace("i/", "#i$")
                .replace("d/", "#d$")
                .replace("l/", "#l$")
                .replace("{NEWLINE}", "\\n");

    }

    /**
     * Converts the storage entry to a formatted string.
     */
    public static String convertToFormattedString(String storageEntry) {
        return storageEntry.replace("#l$", "l/")
                .replace("#d$", "d/")
                .replace("#i$", "i/")
                .replace("\\n", "\n");
    }

    /**
     * Returns the formatted truncated log entry.
     */
    public String getTruncatedEntry() {
        return entry.length() > 100
                ? getFormattedEntry().substring(0, 100) + "..."
                : getFormattedEntry();
    }

    /**
     * Returns true if a given string is a valid entry.
     */
    public static boolean isValidEntry(String test) {
        return !test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if both log entries are the same.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LogEntry // instanceof handles nulls
                && entry.equals(((LogEntry) other).entry)); // state check
    }

    /**
     * Returns the hashcode of the log entry.
     */
    @Override
    public int hashCode() {
        return entry.hashCode();
    }
}
