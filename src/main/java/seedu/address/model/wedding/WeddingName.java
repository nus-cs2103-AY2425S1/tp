package seedu.address.model.wedding;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a {@code Wedding}'s name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class WeddingName {
    public static final String MESSAGE_CONSTRAINTS =
            "Wedding names should only contain alphanumeric characters, spaces or apostrophes, "
                    + "and they should not be blank. Wedding names are case sensitive.";

    /**
     * Validation regex checks that first character of the wedding name must not be a whitespace,
     * so that " " (a blank string) is not a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}'][\\p{Alnum} ']*";
    private final String weddingName;

    /**
     * Constructs a {@code WeddingName}
     * @param name A valid name for a wedding.
     */
    public WeddingName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        weddingName = name;
    }

    /**
     * Returns true if the given name matched the validation regex.
     * @param nameToCheck A {@code String} with the name of the wedding.
     * @return A {@code boolean}, true if the name is valid based on the validation regex, false if not.
     */
    public static boolean isValidName(String nameToCheck) {
        return nameToCheck.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return weddingName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof WeddingName)) {
            return false;
        }

        WeddingName otherWeddingName = (WeddingName) obj;
        return this.weddingName.equalsIgnoreCase(otherWeddingName.weddingName);
    }

    @Override
    public int hashCode() {
        return weddingName.hashCode();
    }

    /**
     * Checks if the {@code WeddingName} object matches the given validation regex
     * by checking the internal {@code WeddingName}
     * @param validationRegex A {@code String} with the validation regex to check the wedding name against
     * @return true if wedding name matches the validation regex, false if not.
     */
    public boolean matches(String validationRegex) {
        return weddingName.matches(validationRegex);
    }
}
