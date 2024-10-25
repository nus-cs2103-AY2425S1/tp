package tahub.contacts.model.person;

import static java.util.Objects.requireNonNull;
import static tahub.contacts.commons.util.AppUtil.checkArgument;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Represents a Person's matriculation number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMatriculationNumber(String)}
 */
public class MatriculationNumber {

    public static final String MESSAGE_CONSTRAINTS = "Matriculation number should be of the "
            + "format A<7 digits><Uppercase alphabetical character>, e.g., A1234567Z";
    public static final String VALIDATION_REGEX = "^A\\d{7}[A-Z]$"; // A followed by 7 digits and an uppercase letter

    public final String value;

    /**
     * Constructs an {@code MatriculationNumber}.
     *
     * @param matricNumber A valid matricNumber.
     */
    @JsonCreator
    public MatriculationNumber(String matricNumber) {
        requireNonNull(matricNumber);
        checkArgument(isValidMatriculationNumber(matricNumber), MESSAGE_CONSTRAINTS);
        value = matricNumber;
    }

    /**
     * Returns if a given string is a valid matriculation number.
     */
    public static boolean isValidMatriculationNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    @JsonValue
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MatriculationNumber)) {
            return false;
        }

        MatriculationNumber otherMatricNumber = (MatriculationNumber) other;
        return value.equals(otherMatricNumber.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
