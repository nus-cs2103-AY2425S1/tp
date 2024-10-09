package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.lesson.LocationIndex.isValidLocationIndex;
import seedu.address.commons.util.NumbersUtil;
import seedu.address.logic.parser.exceptions.ParseException;

public class StudentId {
    public static final String MESSAGE_CONSTRAINTS = "Student ID must be a non-negative integer.";
    public static final String NOT_FOUND_MESSAGE_CONSTRAINTS = "Student ID must be a non-negative integer.";
    private final int value;

    /**
     * Constructs a {@code StudentId}.
     *
     * @param value A valid student ID.
     */
    public StudentId(String value) throws ParseException {
        value = value.trim();
        requireNonNull(value);
        checkArgument(isValidStudentId(value), MESSAGE_CONSTRAINTS);
        this.value = NumbersUtil.parseInt(value, MESSAGE_CONSTRAINTS);
    }

    /**
     * Returns true if a given string is a valid student ID.
     * @param value The student ID to be checked.
     * @return True if the student ID is valid, false otherwise.
     */
    public static boolean isValidStudentId(String value) {
        try {
            int parsedValue = NumbersUtil.parseInt(value, MESSAGE_CONSTRAINTS);
            return parsedValue > 0;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Returns the value of the student ID.
     * @return The value of the student ID.
     */
    public int getValue() {
        return value - 1;
    }
}
