package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
/**
 * Represents the position of the supplier inside the addressbook that is involved in the delivery.
 */
public class SupplierIndex {
    public static final String MESSAGE_CONSTRAINTS =
            "SUPPLIER_INDEX should be a positive number greater than 0"
                    + " and smaller than total number of suppliers";
    public final int index;

    /**
     * Constructs a {@code SupplierIndex}.
     *
     * @param index A index string.
     */
    public SupplierIndex(String index) {
        requireNonNull(index);
        checkArgument(isValidIndex(index), MESSAGE_CONSTRAINTS);
        this.index = Integer.parseInt(index);
    }

    /**
     * Returns true if the given string represents a positive-ingeger.
     *
     * @param test String value of Supplier_index provided by user.
     * @return True if test is an nteger and false otherwise.
     */
    public static boolean isValidIndex(String test) {
        try {
            Integer.parseInt(test);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "" + index;
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
        return index == otherSupplierIndex.index;
    }

    @Override
    public int hashCode() {
        return ((Integer) index).hashCode();
    }

    /**
     * Represents the index provided by user minus 1.
     *
     * @return Index of Supplier in ObservableList.
     */
    public int getZeroBased() {
        return this.index - 1;
    }
}
