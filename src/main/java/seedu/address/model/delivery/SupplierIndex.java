package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
/**
 * Represents the position of the supplier inside the addressbook that is involved in the delivery.
 */
public class SupplierIndex {
    public static final String MESSAGE_CONSTRAINTS =
            "SupplierIndex should be a positive number greater than 0"
                    + " and smaller than total number of suppliers";
    public final String index;

    /**
     * Constructs a {@code SupplierIndex}.
     *
     * @param index A index string.
     */
    public SupplierIndex(String index) {
        requireNonNull(index);
        checkArgument(isValidIndex(index), MESSAGE_CONSTRAINTS);
        this.index = index;
    }

    /**
     * Returns true if the given string is a valid index.
     */
    public static boolean isValidIndex(String test) {
        try {
            Integer.parseInt(test);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    @Override
    public String toString() {
        return index;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SupplierIndex)) {
            return false;
        }

        SupplierIndex otherSupplierIndex = (SupplierIndex) other;
        return index.equals(otherSupplierIndex.index);
    }

    @Override
    public int hashCode() {
        return index.hashCode();
    }
}
