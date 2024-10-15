package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's sex in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSex(String)}
 */
public class Sex {

    public static final String MESSAGE_CONSTRAINTS =
            "Sex should only contain alphabets, and it should not be blank";

    public static final String VALIDATION_REGEX = "[a-zA-Z]";

    public final String sex;

    /**
     * Constructs a {@code Sex}.
     *
     * @param sex A valid name.
     */
    public Sex(String sex) {
        requireNonNull(sex);
        checkArgument(isValidSex(sex), MESSAGE_CONSTRAINTS);
        this.sex = sex;
    }

    /**
     * Returns true if a given string is a valid sex.
     */
    public static boolean isValidSex(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return sex;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return sex.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return sex.hashCode();
    }

}
