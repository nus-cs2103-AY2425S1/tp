package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Delivery Item's id in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidItemId(String)}
 */
public class ItemId {

    public static final String MESSAGE_CONSTRAINTS =
            "ItemId should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String itemId;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public ItemId(String name) {
        requireNonNull(name);
        checkArgument(isValidItemId(name), MESSAGE_CONSTRAINTS);
        itemId = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidItemId(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return itemId;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ItemId)) {
            return false;
        }

        ItemId otherItemId = (ItemId) other;
        return itemId.equals(otherItemId.itemId);
    }

    @Override
    public int hashCode() {
        return itemId.hashCode();
    }

}

