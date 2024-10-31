package seedu.address.model.supplier;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Supplier's company in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidCompany(String)}
 */
public class Company {
    public static final String MESSAGE_CONSTRAINTS =
            "Companies should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the company must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "\\s*[\\p{Alnum}\\p{Punct}][\\p{Alnum}\\p{Punct} ]*\\s*";

    public final String value;

    /**
     * Constructs a {@code Company}.
     *
     * @param company A valid company.
     */
    public Company(String company) {
        requireNonNull(company);
        company = normalizeCompanyName(company);
        this.value = company;
    }

    /**
     * Returns true if a given string is a valid company.
     */
    public static boolean isValidCompany(String test) {
        return test != null && test.matches(VALIDATION_REGEX);
    }

    /**
     * Normalizes the company name by trimming spaces, converting to lower case,
     * and removing extra spaces between words.
     */
    private String normalizeCompanyName(String company) {
        return company.trim().toLowerCase().replaceAll("\\s+", " ");
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
