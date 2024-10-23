package seedu.address.testutil;

import seedu.address.commons.core.index.Index;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalIndexes {
    public static final Index INDEX_FIRST_STUDENT = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_STUDENT = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_STUDENT = Index.fromOneBased(3);

    /**
     * We separate Student indexes from Consult indexes in case the number of sample data is
     * vastly different in the future such that indexes for students may not be valid for consults
     * and vice versa.
     */

    public static final Index INDEX_FIRST_CONSULT = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_CONSULT = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_CONSULT = Index.fromOneBased(3);
}
