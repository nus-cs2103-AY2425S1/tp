package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Client's Company's name in the company book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCompany(String)}
 */
public class Company {

    public static final String MESSAGE_CONSTRAINTS = "Company can take any values, and it should not be blank";

    /*
     * The first character of the company must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Company}.
     *
     * @param company A valid company name.
     */
    public Company(String company) {
        requireNonNull(company);
        checkArgument(isValidCompany(company), MESSAGE_CONSTRAINTS);
        value = company;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidCompany(String test) {
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
        if (!(other instanceof Company)) {
            return false;
        }

        Company otherCompany = (Company) other;
        return value.equals(otherCompany.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
