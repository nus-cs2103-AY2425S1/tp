package seedu.address.commons.core.index;

import java.util.Comparator;

/**
 * Represents a comparator for sorting indices in descending order.
 */
public class DescendingIndexComparator implements Comparator<Index> {
    @Override
    public int compare(Index i1, Index i2) {
        return i2.getZeroBased() - i1.getZeroBased();
    }
}
