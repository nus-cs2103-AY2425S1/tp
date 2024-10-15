package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Delivery Item's id in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidItemId(String)}
 */
public class ItemName {

    public static final String MESSAGE_CONSTRAINTS =
            "Item Name should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String itemName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public ItemName(String name) {
        requireNonNull(name);
        checkArgument(isValidItemId(name), MESSAGE_CONSTRAINTS);
        itemName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidItemId(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return "Item: " + itemName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ItemName)) {
            return false;
        }

        ItemName otherItemId = (ItemName) other;
        return itemName.equals(otherItemId.itemName);
    }

    @Override
    public int hashCode() {
        return itemName.hashCode();
    }

}

