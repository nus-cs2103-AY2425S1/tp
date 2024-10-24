package seedu.address.model.tag;

import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.commons.util.StringUtil;

/**
 * Represents a PersonTag in the address book.
 */
public class PersonTag extends Tag {

    private final PersonTagType personTagType;

    /**
     * Constructs a {@code PersonTag}.
     *
     * @param tagName A valid tag name.
     */
    public PersonTag(String tagName) {
        super(StringUtil.capitaliseFirstLetter(tagName));
        checkArgument(PersonTagType.isValidPersonTag(tagName),
                "Invalid PersonTag. Allowed: Buyer, Seller, Landlord, Tenant");
        this.personTagType = PersonTagType.fromString(tagName);
    }

    /**
     * Returns the tag type, used for differentiating various tag types.
     */
    @Override
    public String getTagType() {
        return "person";
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return "[" + StringUtil.capitaliseFirstLetter(personTagType.name()) + "]";
    }
}

