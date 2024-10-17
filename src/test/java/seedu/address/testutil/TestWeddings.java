package seedu.address.testutil;

import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * A utility class containing a list of {@code Wedding} objects to be used in tests.
 */
public class TestWeddings {
    public static final WeddingName VALID_WEDDING_NAME = new WeddingName("Amy's Wedding");
    public static final Wedding VALID_WEDDING = new Wedding(VALID_WEDDING_NAME);
}
