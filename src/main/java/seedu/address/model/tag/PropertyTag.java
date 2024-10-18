package seedu.address.model.tag;

import seedu.address.commons.util.StringUtil;

import static seedu.address.commons.util.AppUtil.checkArgument;

public class PropertyTag extends Tag {

    private final PropertyTagType propertyTagType;

    public PropertyTag(String tagName) {
        super(StringUtil.capitaliseFirstLetter(tagName));
        checkArgument(PropertyTagType.isValidPropertyTag(tagName),
                "Invalid PropertyTag. Allowed: HDB, Condo, Landed");
        this.propertyTagType = PropertyTagType.fromString(tagName);
    }

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
