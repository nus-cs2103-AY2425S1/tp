package seedu.address.model.tag;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a PropertyTag in the address book.
 */
public class PropertyTag extends Tag {

    public static final String ALLOWED_PROPERTY_TAGS = "HDB, CONDO, RESIDENTIAL, LANDED, EC, COMMERCIAL,\n"
            + "    RETAIL, INDUSTRIAL, OFFICE, WAREHOUSE, SHOPHOUSE, TERRACE,\n"
            + "    SEMIDET, BUNGALOW, DETACHED, GCB, PENTHOUSE, MIXED,\n"
            + "    SERVAPT, DORM";
    private final PropertyTagType propertyTagType;

    /**
     * Constructs a {@code PropertyTag}.
     *
     * @param tagName A valid tag name.
     */
    public PropertyTag(String tagName) {
        super(normaliseTagName(tagName));
        checkArgument(PropertyTagType.isValidPropertyTag(tagName),
                "Invalid PropertyTag. Allowed (case-insensitive): " + ALLOWED_PROPERTY_TAGS);
        this.propertyTagType = PropertyTagType.fromString(tagName);
    }

    /**
     * Normalises the tag name to ensure all tags are in uppercase.
     */
    private static String normaliseTagName(String tagName) {
        return tagName.toUpperCase();
    }

    /**
     * Returns the tag type, used for differentiating various tag types.
     */
    @Override
    public String getTagType() {
        return "property";
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return "[" + propertyTagType.name() + "]";
    }
}
