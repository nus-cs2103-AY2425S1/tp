package seedu.address.model.supplier;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.person.Name;

/**
 * Represents a Supplier's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class SupplierName {
    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String supplierName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public SupplierName(String name) {
        requireNonNull(name);
        name = normalizeCompanyName(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        supplierName = name;
    }

    /**
     * Normalizes the supplier name by trimming spaces, converting to lower case,
     * and removing extra spaces between words.
     */
    private String normalizeCompanyName(String supplierName) {
        return supplierName.trim().toLowerCase().replaceAll("\\s+", " ");
    }
    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }
    @Override
    public String toString() {
        return supplierName;
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
        return supplierName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return supplierName.hashCode();
    }
}
