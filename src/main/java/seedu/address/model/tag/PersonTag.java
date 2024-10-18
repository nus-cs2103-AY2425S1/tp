package seedu.address.model.tag;

import seedu.address.commons.util.StringUtil;

import java.util.Arrays;

import static seedu.address.commons.util.AppUtil.checkArgument;

public class PersonTag extends Tag {

    private final PersonTagType personTagType;

    public PersonTag(String tagName) {
        super(StringUtil.capitaliseFirstLetter(tagName));
        checkArgument(PersonTagType.isValidPersonTag(tagName),
                "Invalid PersonTag. Allowed: Buyer, Seller, Landlord, Tenant");
        this.personTagType = PersonTagType.fromString(tagName);
    }

    @Override
    public String toString() {
        return "[" + StringUtil.capitaliseFirstLetter(personTagType.name()) + "]";
    }
}

