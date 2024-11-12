package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book. Guarantees: immutable; is valid as declared in
 * {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS = "Name should only contain alphabet characters and spaces.\n"
            + "Both given name and surname should be included.";

    /*
     * The name must only contain alphabetical characters and spaces.
     * It must contain at least two words, for given name and surname.
     */
    public static final String VALIDATION_REGEX = "([a-zA-Z]+ )+[a-zA-Z]+";

    public final String fullName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     *
     * @return true if valid, otherwise false.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullName;
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
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
