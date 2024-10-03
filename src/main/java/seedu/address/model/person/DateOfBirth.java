package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.commons.util.DateUtil;

/**
 * Represents a patient's date of birth in MediBase3.
 */
public class DateOfBirth extends DateUtil {
    public static final String MESSAGE_CONSTRAINTS = "Date of birth should not be after today's date";

    /**
     * Constructs a {@code DateOfBirth}.
     *
     * @param dob A valid date of birth.
     */
    public DateOfBirth(String dob) {
        super(dob);
        checkArgument(isValidDate(dob), MESSAGE_CONSTRAINTS);
    }

    /**
     * Returns if a given string is a valid date of birth.
     *
     * @param dob The date of birth to be checked.
     */
    public static boolean isValidDateOfBirth(String dob) {
        return isAfterToday(dob);
    }
}
