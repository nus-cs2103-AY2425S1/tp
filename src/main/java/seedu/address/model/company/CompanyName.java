package seedu.address.model.company;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a company's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class CompanyName {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain ASCII characters and spaces, and it should not be blank";

    /**
     * The first character address must be a visible character,
     * otherwise, " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Graph}][\\p{ASCII} ]*";

    public final String fullName;

    /**
     * Constructs a <code>CompanyName</code>.
     *
     * @param name A valid name of a company.
     */
    public CompanyName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.fullName = name;
    }

    /**
     * Checks if the String is a valid company name.
     *
     * @param test String to check if valid.
     * @return true if valid.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CompanyName)) {
            return false;
        }

        CompanyName otherCompanyName = (CompanyName) other;
        return this.fullName.equals(otherCompanyName.fullName);
    }

    @Override
    public int hashCode() {
        return this.fullName.hashCode();
    }
}
