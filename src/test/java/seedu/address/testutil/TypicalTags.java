package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * A utility class containing a list of {@code Tag} objects to be used in tests.
 */
public class TypicalTags {
    public static final Tag TEST = new Tag("test");
    public static final Tag FRIENDS_TAG = new Tag("friends");
    public static final Set<Tag> TEST_SET = new HashSet<>();
}
