package seedu.address.commons.core.index;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a zero-based or one-based index.
 *
 * {@code Index} should be used right from the start (when parsing in a new user input), so that if the current
 * component wants to communicate with another component, it can send an {@code Index} to avoid having to know what
 * base the other component is using for its index. However, after receiving the {@code Index}, that component can
 * convert it back to an int if the index will not be passed to a different component again.
 */
public class Index {
    private static final Index WILDCARD = new Index();
    private Integer zeroBasedIndex;

    /**
     * Index can only be created by calling {@link Index#fromZeroBased(int)} or
     * {@link Index#fromOneBased(int)} or retrieved from {@link #getWildcardIndex()}.
     */
    private Index(int zeroBasedIndex) {
        if (zeroBasedIndex < 0) {
            throw new IndexOutOfBoundsException();
        }
        this.zeroBasedIndex = zeroBasedIndex;
    }

    private Index() {
        // prevent zeroBasedIndex from being defaulted to 0
        this.zeroBasedIndex = null;
    };

    public int getZeroBased() {
        if (this.isWildcard()) {
            throw new UnsupportedOperationException("Wildcard index does not have a zero-based index.");
        }
        return zeroBasedIndex;
    }

    public int getOneBased() {
        if (this.isWildcard()) {
            throw new UnsupportedOperationException("Wildcard index does not have a one-based index.");
        }
        return zeroBasedIndex + 1;
    }

    /**
     * Creates a new {@code Index} using a zero-based index.
     */
    public static Index fromZeroBased(int zeroBasedIndex) {
        return new Index(zeroBasedIndex);
    }

    /**
     * Creates a new {@code Index} using a one-based index.
     */
    public static Index fromOneBased(int oneBasedIndex) {
        return new Index(oneBasedIndex - 1);
    }

    /**
     * Returns the wildcard index.
     */
    public static Index getWildcardIndex() {
        return Index.WILDCARD;
    }

    /**
     * Returns true if this index is a wildcard index.
     */
    public boolean isWildcard() {
        return this == Index.WILDCARD;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Index)) {
            return false;
        }

        Index otherIndex = (Index) other;
        return zeroBasedIndex == otherIndex.zeroBasedIndex;
    }

    @Override
    public String toString() {
        if (this.isWildcard()) {
            return Index.class.getCanonicalName() + "{wildcard}";
        }
        return new ToStringBuilder(this).add("zeroBasedIndex", zeroBasedIndex).toString();
    }
}
