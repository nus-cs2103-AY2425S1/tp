package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a person's emergency contact name in the address book
 * The name can be empty or contain only alphanumeric characters and spaces.
 */
public class EcName implements Comparable<EcName> {

    public static final String MESSAGE_GUI = "Emergency Contact Name: %1$s";
    public static final String MESSAGE_CONSTRAINTS =
            "Emergency contact names should only contain alphanumeric characters and spaces, "
            + "and it can be blank if no contact name is provided.";

    /**
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public final String value;


    /**
     * Constructs a {@code ECName}
     *
     * @param ecName A valid emergency contact name.
     */
    public EcName(String ecName) {
        requireNonNull(ecName);
        checkArgument(isValidEcName(ecName), MESSAGE_CONSTRAINTS);
        value = ecName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidEcName(String name) {
        return name != null && (name.matches(VALIDATION_REGEX) || name.isEmpty());
    }

    /**
     * Returns true if the value of EcName is empty.
     */
    public boolean isEmpty() {
        return value.isEmpty();
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
        if (!(other instanceof EcName)) {
            return false;
        }

        EcName otherName = (EcName) other;
        return value.equals(otherName.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * {@inheritDoc}
     *
     * If the String is empty, the String will be considered to be greater than a non-empty String.
     */
    @Override
    public int compareTo(EcName e) {
        assert e != null;
        if (this.isEmpty() || e.isEmpty()) {
            return e.toString().compareTo(this.toString());
        } else {
            return this.toString().compareTo(e.toString());
        }
    }
}
