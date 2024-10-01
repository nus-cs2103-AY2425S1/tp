package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Company's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCompanyName(String)}
 */
public class Company {

    public static final String MESSAGE_CONSTRAINTS =
            "Company name should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String companyName;

    /**
     * Constructs a {@code Name}.
     *
     * @param company A valid name.
     */
    public Company(String company) {
        requireNonNull(company);
        checkArgument(isValidCompanyName(company), MESSAGE_CONSTRAINTS);
        companyName = company;
    }

    /**
     * Returns true if a given string is a valid name.
     *
     * @param test The string to test.
     * @return True if the given string matches the validation regex, false otherwise.
     */
    public static boolean isValidCompanyName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return companyName;
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
        return companyName.equals(otherName.companyName);
    }

    @Override
    public int hashCode() {
        return companyName.hashCode();
    }

}
