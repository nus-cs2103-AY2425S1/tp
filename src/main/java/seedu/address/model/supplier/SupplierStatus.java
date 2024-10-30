package seedu.address.model.supplier;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Supplier's status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class SupplierStatus {
    public static final String MESSAGE_CONSTRAINTS = "Status should be either active or inactive";
    public static final String VALIDATION_REGEX = "^(active|inactive)$";
    public final String status;

    /**
     * Constructs a {@code SupplierStatus}.
     *
     * @param status A valid status.
     */
    public SupplierStatus(String status) {
        checkArgument(isValidStatus(status.trim()), MESSAGE_CONSTRAINTS);
        this.status = status.trim();
    }

    public static boolean isValidStatus(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SupplierStatus
                && status.equals(((SupplierStatus) other).status));
    }
    @Override
    public int hashCode() {
        return status.hashCode();
    }
}
