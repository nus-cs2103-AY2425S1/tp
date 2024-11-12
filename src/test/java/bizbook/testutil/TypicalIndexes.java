package bizbook.testutil;

import bizbook.commons.core.index.Index;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalIndexes {
    public static final Index INDEX_FIRST_PERSON = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_PERSON = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_PERSON = Index.fromOneBased(3);
    public static final Index INDEX_OUTOFBOUND_PERSON = Index.fromOneBased(1000);

    public static final Index INDEX_FIRST_NOTE = Index.fromOneBased(1);
    public static final Index INDEX_OUTOFBOUND_NOTE = Index.fromOneBased(1000);
}
