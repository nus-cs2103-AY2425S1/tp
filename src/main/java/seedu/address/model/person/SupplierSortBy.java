package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.logic.parser.SortOrder;

/**
 * Represents a field to sort suppliers by.
 */
public class SupplierSortBy {
    /**
     * Enum to represent the field to sort suppliers by.
     */
    public enum SupplierSortByField {
        N
    }
    public static final String MESSAGE_CONSTRAINTS = "Supplier sort by field should be 'n' for name";
    /**
     * Field to sort by must be 'n'.
     */
    public static final String VALIDATION_REGEX = "^[n]$";
    private final SupplierSortByField field;
    /**
     * Creates a SupplierSortBy object with the corresponding field to sort by.
     * @param field Represents name with 'n'.
     */
    public SupplierSortBy(String field) {
        requireNonNull(field);
        checkArgument(isValidSortBy(field), MESSAGE_CONSTRAINTS);
        switch (SupplierSortByField.valueOf(field.toUpperCase())) {
        case N:
            this.field = SupplierSortByField.N;
            break;
        default:
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }
    /**
     * Returns the field to sort suppliers by.
     */
    public SupplierSortByField getField() {
        return field;
    }
    /**
     * Returns the corresponding comparator of {@code sortBy} which compares by the {@code sortOrder}.
     */
    public static SupplierSortComparator getSupplierSortComparator(SortOrder sortOrder, SupplierSortBy supplierSortBy) {
        switch (supplierSortBy.getField()) {
        case N:
            return new SupplierSortNameComparator(sortOrder);
        default:
            return null;
        }
    }
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SupplierSortBy // instanceof handles nulls
                        && field.equals(((SupplierSortBy) other).field)); // state check
    }
    public static boolean isValidSortBy(String field) {
        return field.matches(VALIDATION_REGEX);
    }
    @Override
    public String toString() {
        return field.toString();
    }
}
