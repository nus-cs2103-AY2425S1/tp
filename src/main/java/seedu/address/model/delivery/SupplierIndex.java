package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
/**
 * Represents the position of the supplier in the currently displayed list of VendorVault
 * that is involved in the delivery.
 */
public class SupplierIndex {
    public static final String MESSAGE_CONSTRAINTS =
            "SUPPLIER_INDEX should be a positive number greater than 0"
                    + " and smaller than the total number of suppliers added\n"
                    + "and must not be blank.";
    private final int index;

    /**
     * Constructs a {@code SupplierIndex}.
     *
     * @param index Index String value.
     */
    public SupplierIndex(String index) {
        requireNonNull(index);
        checkArgument(isValidIndex(index), MESSAGE_CONSTRAINTS);
        this.index = Integer.parseInt(index);
    }

    /**
     * Returns true if the given string represents an integer.
     *
     * @param test String value of SupplierIndex provided by user.
     * @return True if input is an integer and false otherwise.
     */
    public static boolean isValidIndex(String test) {
        try {
            Integer.parseInt(test);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Represents the String value of SupplierIndex.
     */
    @Override
    public String toString() {
        return "" + index;
    }
    /**
     * Returns true if index of both objects are same.
     *
     * @param other Object to be compared with.
     * @return True if object is an instance of SupplierIndex and has the same integer index value.
     */
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

    /**
     * Creates a hash of the index value.
     *
     * @return Hash value of integer index value.
     */
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
