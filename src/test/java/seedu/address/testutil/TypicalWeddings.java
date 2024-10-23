package seedu.address.testutil;

import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingName;

/**
 * A utility class containing a list of {@code Wedding} objects to be used in tests.
 */
public class TypicalWeddings {
    public static final String VALID_WEDDING_STRING_AMY_WEDDING = "Amy's Wedding";
    public static final String VALID_WEDDING_STRING_BOB_WEDDING = "Bob's Wedding";
    public static final WeddingName VALID_WEDDING_NAME_AMY_WEDDING = new WeddingName(VALID_WEDDING_STRING_AMY_WEDDING);
    public static final WeddingName VALID_WEDDING_NAME_BOB_WEDDING = new WeddingName(VALID_WEDDING_STRING_BOB_WEDDING);
    public static final Wedding AMY_WEDDING = new Wedding(VALID_WEDDING_NAME_AMY_WEDDING);
    public static final Wedding BOB_WEDDING = new Wedding(VALID_WEDDING_NAME_BOB_WEDDING);
}
