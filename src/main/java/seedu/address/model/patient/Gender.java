package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's gender in the contacts book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender {

    private enum PatientGender {
        MALE,
        FEMALE,
        OTHER
    }

    public static final String MESSAGE_CONSTRAINTS = "Gender can be either M (Male), F (Female) or O (Other)";

    /*
     * A single "M", "F" or "O" character.
     */
    public static final String VALIDATION_REGEX = "^[MFO]$";

    public final PatientGender gender;

    /**
     * Constructs an {@code Gender}.
     *
     * @param input A valid gender.
     */
    public Gender(String input) {
        requireNonNull(input);
        checkArgument(isValidGender(input), MESSAGE_CONSTRAINTS);

        if (input.equals("M")) {
            gender = PatientGender.MALE;
        } else if (input.equals("F")) {
            gender = PatientGender.FEMALE;
        } else {
            // input is guaranteed to be "O" here
            assert(input.equals("O"));
            gender = PatientGender.OTHER;
        }
    }

    /**
     * Returns true if a given string is a valid gender.
     */
    public static boolean isValidGender(String input) {
        return input.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        if (gender.equals(PatientGender.MALE)) {
            return "M";
        } else if (gender.equals(PatientGender.FEMALE)) {
            return "F";
        }

        // gender is guaranteed to be OTHER here
        assert(gender.equals(PatientGender.OTHER));
        return "O";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Gender)) {
            return false;
        }

        Gender otherGender = (Gender) other;
        return gender.equals(otherGender.gender);
    }

    @Override
    public int hashCode() {
        return gender.hashCode();
    }

}
