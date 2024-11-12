package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.tag.Tag;

/**
 * A utility class containing a list of {@code Tag} objects to be used in tests.
 */
public class TypicalTags {
    public static final Tag BRIDES_SIDE = new Tag("bride's side");
    public static final Tag GROOMS_SIDE = new Tag("groom's side");
    public static final Tag FRIENDS = new Tag("friends");
    public static final Tag COLLEAGUES = new Tag("colleagues");
    public static final Tag FAMILY = new Tag("family");
    public static final Tag VIP = new Tag("VIP");

    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Tag tag : getTypicalTags()) {
            ab.addTag(tag);
        }
        return ab;
    }

    public static List<Tag> getTypicalTags() {
        return new ArrayList<>(Arrays.asList(GROOMS_SIDE, FRIENDS, COLLEAGUES, FAMILY, VIP));
    }
}
