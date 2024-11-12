package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's organisation in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidOrganisation(String)}
 */
public class Organisation {

    public static final String MESSAGE_CONSTRAINTS = "Organisation can take any values, and it should not be blank";

    /*
     * The first character of the Organisation must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code Organisation}.
     *
     * @param organisation A valid organisation.
     */
    public Organisation(String organisation) {
        requireNonNull(organisation);
        checkArgument(isValidOrganisation(organisation), MESSAGE_CONSTRAINTS);
        value = organisation;
    }

    /**
     * Returns true if a given string is a valid organisation.
     */
    public static boolean isValidOrganisation(String test) {
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
        if (!(other instanceof Organisation)) {
            return false;
        }

        Organisation otherOrganisation = (Organisation) other;
        return value.equals(otherOrganisation.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
