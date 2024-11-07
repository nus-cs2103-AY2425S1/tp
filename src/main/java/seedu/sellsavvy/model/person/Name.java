package seedu.sellsavvy.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.sellsavvy.commons.util.AppUtil.checkArgument;
import static seedu.sellsavvy.commons.util.StringUtil.normalise;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names can only contain alphanumeric characters, spaces, "
                    + "and one of the following symbols: hyphen, comma, and apostrophe, where commas should be "
                    + "followed with a space.\n"
                    + "Names should have alphanumeric characters before and after the symbol, "
                    + "and it should not be blank.\n"
                    + "Relationship indicator using \"S/O\" or \"D/O\" can be included but should be wrapped "
                    + "with spaces, and followed with the name of the person with stated relationship.";

    /*
     * The first character of a name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String ALPHANUMERIC_WITH_SPACE = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String NAME_REGEX = ALPHANUMERIC_WITH_SPACE + "((,\\s|'|-)" + ALPHANUMERIC_WITH_SPACE + ")?";
    public static final String RELATIONSHIP_REGEX = "\\s(S/O|D/O)\\s" + NAME_REGEX;
    public static final String VALIDATION_REGEX = "^" + NAME_REGEX + "(" + RELATIONSHIP_REGEX + ")?$";

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
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if the name is similar to {@code otherName}.
     * Two names are considered similar if they are the same without considering space and casing.
     */
    public boolean isSimilarTo(Name otherName) {
        return normalise(this.fullName).equals(normalise(otherName.fullName));
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
