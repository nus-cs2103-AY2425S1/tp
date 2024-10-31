package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalIndexes {
    public static final Index INDEX_FIRST = Index.fromOneBased(1);
    public static final Index INDEX_SECOND = Index.fromOneBased(2);
    public static final Index INDEX_THIRD = Index.fromOneBased(3);
    public static final List<Index> INDEX_FIRST_LIST = new ArrayList<>(Arrays.asList(INDEX_FIRST));
    public static final List<Index> INDEX_SECOND_LIST = new ArrayList<>(Arrays.asList(INDEX_SECOND));
    public static final List<Index> INDEX_FIRST_SECOND_LIST = new ArrayList<>(Arrays.asList(
            INDEX_FIRST, INDEX_SECOND));
    public static final List<Index> INDEX_SECOND_THIRD_LIST = new ArrayList<>(Arrays.asList(
            INDEX_SECOND, INDEX_THIRD));
    public static final List<Index> INDEX_DUPLICATES_LIST = new ArrayList<>(Arrays.asList(
            INDEX_FIRST, INDEX_SECOND, INDEX_SECOND, INDEX_THIRD));
}
