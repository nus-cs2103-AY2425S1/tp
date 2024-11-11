package seedu.sellsavvy.model.order;

import static java.util.Objects.requireNonNull;
import static seedu.sellsavvy.commons.util.AppUtil.checkArgument;
import static seedu.sellsavvy.commons.util.StringUtil.normalise;

/**
 * Represents the item description of a Customer's Order in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidItem(String)}
 */
public class Item {

    public static final String MESSAGE_CONSTRAINTS =
            "Item can contain any printable characters, but it should not be blank.";

    /*
     * The first character of the item must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^(?!\\s*$)[\\p{Print}]+$";

    public final String fullDescription;

    /**
     * Constructs a {@code Item}.
     *
     * @param item A valid item description.
     */
    public Item(String item) {
        requireNonNull(item);
        checkArgument(isValidItem(item), MESSAGE_CONSTRAINTS);
        fullDescription = item;
    }

    /**
     * Returns true if a given string is a valid item description.
     */
    public static boolean isValidItem(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if this item's full description is similar to {@code otherItem}'s full description.
     * Two item's full description are considered similar if they are same without considering space and casing.
     */
    public boolean isSimilarTo(Item otherItem) {
        return normalise(this.fullDescription).equals(normalise(otherItem.fullDescription));
    }

    @Override
    public String toString() {
        return fullDescription;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Item)) {
            return false;
        }

        Item otherItem = (Item) other;
        return fullDescription.equals(otherItem.fullDescription);
    }

    @Override
    public int hashCode() {
        return fullDescription.hashCode();
    }

}
