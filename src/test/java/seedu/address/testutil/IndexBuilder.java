package seedu.address.testutil;

import seedu.address.commons.core.index.Index;

/**
 * A utility class to help with building Index objects.
 */
public class IndexBuilder {
    private final int index;
    public IndexBuilder() {
        index = 1;
    }

    public Index build() {
        return Index.fromZeroBased(index);
    }
}
