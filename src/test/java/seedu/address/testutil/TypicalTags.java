package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FLORIST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PHOTOGRAPHER;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagName;

/**
 * A utility class containing a list of {@code Wedding} objects to be used in tests.
 */
public class TypicalTags {
    public static final TagName VALID_TAG_NAME_FLORIST = new TagName(VALID_TAG_FLORIST);
    public static final TagName VALID_TAG_NAME_PHOTOGRAPHER = new TagName(VALID_TAG_PHOTOGRAPHER);
    public static final Tag FLORIST = new Tag(VALID_TAG_NAME_FLORIST);
    public static final Tag PHOTOGRAPHER = new Tag(VALID_TAG_NAME_PHOTOGRAPHER);
}
