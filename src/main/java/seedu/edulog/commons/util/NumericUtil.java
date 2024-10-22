package seedu.edulog.commons.util;

/**
 * Helper functions for handling numbers.
 */
public class NumericUtil {
    /**
     * Regular Expression to check if the given string contains only numbers.
     * {@code ^} represents beginning of line.
     * {@code \d} represents numbers.
     * {@code +} matches one or more characters.
     * {@code $} represents end of line.
     */
    public static final String ONLY_NUMBER_REGEX = "^\\d+$";

    /**
     * Checks whether the given string represents a number. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @return {@code true} if the string represents a number, false otherwise.
     */
    public static boolean isNumeric(String number) {
        String trimmedNumber = number.trim();
        return trimmedNumber.matches(ONLY_NUMBER_REGEX);
    }
}
