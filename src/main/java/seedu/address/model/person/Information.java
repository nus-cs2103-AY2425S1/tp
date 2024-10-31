package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Customer's information in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidInformation(String)}.
 */
public class Information {

    public static final String MESSAGE_CONSTRAINTS =
            "Information should not be blank, and it can contain any characters.";

    /*
     * The information field should not be blank.
     */
    public static final String VALIDATION_REGEX = "^(?!\\s*$).+";

    public final String value;

    /**
     * Constructs an {@code Information}.
     *
     * @param information A valid information string.
     */
    public Information(String information) {
        requireNonNull(information);
        checkArgument(isValidInformation(information), MESSAGE_CONSTRAINTS);
        this.value = information;
    }

    public Information() {
        this.value = "";
    }

    /**
     * Returns true if the given string is a valid information string.
     */
    public static boolean isValidInformation(String test) {
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

        if (!(other instanceof Information)) {
            return false;
        }

        Information otherInfo = (Information) other;
        return value.equals(otherInfo.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
