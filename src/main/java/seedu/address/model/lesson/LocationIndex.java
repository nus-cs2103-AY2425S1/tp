package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import seedu.address.commons.util.NumbersUtil;
import seedu.address.logic.parser.exceptions.ParseException;

public class LocationIndex {
    public static final String MESSAGE_CONSTRAINTS= "Location index must be a non-negative integer.";
    private final int value;

    /**
     * Constructs a {@code LocationIndex}.
     *
     * @param value A valid location index.
     */
    public LocationIndex(String value) throws ParseException {
        value = value.trim();
        requireNonNull(value);
        checkArgument(isValidLocationIndex(value), MESSAGE_CONSTRAINTS);
        this.value = NumbersUtil.parseInt(value, MESSAGE_CONSTRAINTS);
    }

    /**
     * Returns true if a given string is a valid location index.
     * @param value The location index to be checked.
     * @return True if the location index is valid, false otherwise.
     */
    public static boolean isValidLocationIndex(String value) throws ParseException {
        try {
            int parsedValue = NumbersUtil.parseInt(value.trim(), MESSAGE_CONSTRAINTS);
            return parsedValue > 0;
        } catch (ParseException e) {
            return false;
        }
    }

    public int getValue() {
        return value - 1;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
