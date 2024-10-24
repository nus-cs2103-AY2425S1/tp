package seedu.address.model.restaurant;

import seedu.address.model.tag.Tag;

/**
 * Represents a Price tag in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPrice(String)}
 */
public class Price extends Tag {

    /**
     * Constructs a Price object with the specified price value.
     *
     * @param tagName A valid price tag name.
     */
    public Price(String tagName) {
        super(tagName);
    }

    /**
     * Constructs a Price object from a Tag.
     *
     * @param tag A Tag object containing the price tag name.
     */
    public Price(Tag tag) {
        super(tag.tagName);
    }
}
