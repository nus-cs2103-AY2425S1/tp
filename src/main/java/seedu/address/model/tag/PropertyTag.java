package seedu.address.model.tag;

import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.commons.util.StringUtil;

/**
 * Represents a PropertyTag in the address book.
 */
public class PropertyTag extends Tag {

    private final PropertyTagType propertyTagType;

    /**
     * Constructs a {@code PropertyTag}.
     *
     * @param tagName A valid tag name.
     */
    public PropertyTag(String tagName) {
        super(StringUtil.capitaliseFirstLetter(tagName));
        checkArgument(PropertyTagType.isValidPropertyTag(tagName),
                "Invalid PropertyTag. Allowed: HDB, Condo, Landed");
        this.propertyTagType = PropertyTagType.fromString(tagName);
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        // Check if the property tag is HDB, in which case return it without any capitalisation changes.
        if (propertyTagType == PropertyTagType.HDB) {
            return "[" + propertyTagType.name() + "]";
        } else {
            return "[" + StringUtil.capitaliseFirstLetter(propertyTagType.name().toLowerCase()) + "]";
        }
    }
}
