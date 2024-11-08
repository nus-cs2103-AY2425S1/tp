package tutorease.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static tutorease.address.commons.util.AppUtil.checkArgument;

import java.util.logging.Level;
import java.util.logging.Logger;

import tutorease.address.commons.core.LogsCenter;
import tutorease.address.commons.util.NumbersUtil;
import tutorease.address.logic.parser.exceptions.ParseException;

/**
 * Represents a Student ID in the lesson schedule.
 */
public class StudentId {
    public static final String MESSAGE_CONSTRAINTS = "Student ID must be a non-negative integer.";
    public static final String INVALID_MESSAGE_CONSTRAINTS = "Invalid student ID.";
    private static Logger logger = LogsCenter.getLogger(StudentId.class);
    private final int value;

    /**
     * Constructs a {@code StudentId}.
     *
     * @param value A valid student ID.
     */
    public StudentId(String value) throws ParseException {
        logger.log(Level.INFO, "Creating StudentId object with value: " + value);
        value = value.trim();
        requireNonNull(value);
        checkArgument(isValidStudentId(value), MESSAGE_CONSTRAINTS);

        this.value = NumbersUtil.parseInt(value, MESSAGE_CONSTRAINTS);
        logger.log(Level.INFO, "Created StudentId object with value: " + value);
    }

    /**
     * Returns true if a given string is a valid student ID.
     *
     * @param value The student ID to be checked.
     * @return True if the student ID is valid, false otherwise.
     */
    public static boolean isValidStudentId(String value) {
        logger.log(Level.INFO, "Checking if student ID is valid: " + value);
        try {
            int parsedValue = NumbersUtil.parseInt(value, MESSAGE_CONSTRAINTS);
            return parsedValue > 0;
        } catch (ParseException e) {
            logger.log(Level.WARNING, "Student ID is invalid: " + value);
            return false;
        }
    }

    /**
     * Returns the value of the student ID.
     *
     * @return The value of the student ID.
     */
    public int getValue() {
        return value - 1;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof StudentId)) {
            return false;
        }

        StudentId otherStudentId = (StudentId) other;
        return value == otherStudentId.value;
    }
}
