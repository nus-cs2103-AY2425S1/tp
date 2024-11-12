package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Company's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCompany(String)}
 */
public class Company {

    public static final String MESSAGE_CONSTRAINTS =
            "Company name should not be blank";

    public final String value;

    /**
     * Constructs a {@code Name}.
     *
     * @param company A valid name.
     */
    public Company(String company) {
        requireNonNull(company);
        checkArgument(isValidCompany(company), MESSAGE_CONSTRAINTS);
        value = company;
    }

    /**
     * Returns true if a given string is a valid name.
     *
     * @param test The string to test.
     * @return True if the given string matches the validation regex, false otherwise.
     */
    public static boolean isValidCompany(String test) {
        return !test.isBlank();
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
        if (!(other instanceof Company)) {
            return false;
        }

        Company otherName = (Company) other;
        return value.equals(otherName.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
