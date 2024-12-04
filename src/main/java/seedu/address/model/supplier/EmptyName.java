package seedu.address.model.supplier;

/**
 * Represents an empty supplier name when no supplier is yet assigned.
 */
public class EmptyName extends Name {
    /**
     * Constructs an {@code EmptyName} instance representing the absence of a supplier.
     */
    public EmptyName() {
        super("No Supplier Assigned");
    }

    /**
     * Returns a string representation indicating no supplier is assigned.
     */
    @Override
    public String toString() {
        return "No Supplier Assigned";
    }

    /**
     * Overrides equals to ensure that all EmptyName instances are considered equal.
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof EmptyName;
    }

    /**
     * Overrides hashCode to be consistent with equals.
     */
    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
