package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Company's name in the internship book.
 * Guarantees: immutable; is valid as declared in {@link #validate(String)}.
 */
public class Name implements Validatable {

    public static final String MESSAGE_CONSTRAINTS =
            "Name should only contain alphanumeric characters, single spaces between words, "
                    + "and certain symbols like '&', '-', '\'', and '.', and it should not start or end with special characters or spaces.";

    /*
     * Company names should start with an alphanumeric character, and can contain the characters &, ', -, . and single spaces between words.
     * No leading/trailing spaces or special characters, and no multiple consecutive spaces are allowed.
     */
    public static final String VALIDATION_REGEX = "^[\\p{Alnum}][\\p{Alnum}&'.-]*(?: [\\p{Alnum}&'.-]+)*$";

    private final String value;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid company name.
     * @throws NullPointerException if the {@code name} is null.
     * @throws IllegalArgumentException if the {@code name} does not satisfy the constraints.
     */
    public Name(String name) {
        requireNonNull(name);
        checkArgument(validate(name), MESSAGE_CONSTRAINTS);
        this.value = name;
    }

    /**
     * Returns the string representation of this company name.
     *
     * @return the company name as a string.
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Returns a string representation of the company name.
     *
     * @return the name as a string.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Compares this company name to another object.
     *
     * @param other the object to compare.
     * @return true if the object is an instance of {@code Name} and has the same value, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return value.equalsIgnoreCase(otherName.value);
    }

    /**
     * Returns the hash code of the company name.
     *
     * @return the hash code of the company name value.
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Validates the given string as a company name.
     * Implements the {@code Validatable} interface's method.
     *
     * @param test the string to be validated.
     * @return true if the string is a valid company name, false otherwise.
     */
    @Override
    public boolean validate(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}
