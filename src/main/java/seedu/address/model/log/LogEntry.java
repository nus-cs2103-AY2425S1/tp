package seedu.address.model.log;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the content of a log in the address book.
 */
public class LogEntry {
    public static final String MESSAGE_CONSTRAINTS = "A Log requires a date in the format of dd MMM yyyy,"
            + "and a log description that can take any characters, including symbols, numbers, etc.\n"
            + "Both date and description should not be blank.\n"
            + "Format: d/date l/log description\n"
            + "e.g. d/20 May 2024 l/First appointment with John. John shared 3 problems during the session.";
    public static final String VALIDATION_REGEX = ".+";
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

    public static String convertToStorageString(String formattedEntry) {
        return formattedEntry.replace("\n", "\\n");
    }

    public static String convertToFormattedString(String storageEntry) {
        return storageEntry.replace("\\n", "\n");
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
