package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's internship/work experience in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWorkExp(String)}
 */
public class WorkExp {

    public static final String MESSAGE_CONSTRAINTS =
            "Work experience should follow the format: Role,Company,Year. Role and Company should contain only "
                    + "alphanumeric characters, spaces, or commas. Year should be a 4-digit number.";

    /*
     * The format must follow: Role,Company,Year where each part must not be blank.
     * The role and company names should start with an uppercase letter.
     * Year should be a 4-digit number.
     */
    public static final String VALIDATION_REGEX = "^[A-Z][a-zA-Z ]*,[A-Z][a-zA-Z &\\-.,_@]*,\\d{4}$|^$";


    public final String value;

    /**
     * Constructs a {@code WorkExp}.
     *
     * @param workExp A valid work experience string.
     */
    public WorkExp(String workExp) {
        checkArgument(isValidWorkExp(workExp), MESSAGE_CONSTRAINTS);
        value = workExp;
    }

    /**
     * Returns true if a given string is a valid work experience entry.
     */
    public static boolean isValidWorkExp(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WorkExp)) {
            return false;
        }

        WorkExp otherWorkExp = (WorkExp) other;
        return value.equals(otherWorkExp.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
