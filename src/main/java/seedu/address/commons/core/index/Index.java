package seedu.address.commons.core.index;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Represents a zero-based or one-based index.
 *
 * {@code Index} should be used right from the start (when parsing in a new user input), so that if the current
 * component wants to communicate with another component, it can send an {@code Index} to avoid having to know what
 * base the other component is using for its index. However, after receiving the {@code Index}, that component can
 * convert it back to an int if the index will not be passed to a different component again.
 */
public class Index implements Comparable<Index> {
    private int zeroBasedIndex;

    /**
     * Index can only be created by calling {@link Index#fromZeroBased(int)} or
     * {@link Index#fromOneBased(int)}.
     */
    private Index(int zeroBasedIndex) throws CommandException {
        if (zeroBasedIndex < 0) {
            throw new CommandException(Messages.MESSAGE_NON_POSITIVE_INDEX);
        }

        this.zeroBasedIndex = zeroBasedIndex;
    }
    @Override
    public int compareTo(Index other) {
        return Integer.compare(other.zeroBasedIndex, this.zeroBasedIndex);
    }

    public int getZeroBased() {
        return zeroBasedIndex;
    }

    public int getOneBased() {
        return zeroBasedIndex + 1;
    }

    /**
     * Creates a new {@code Index} using a zero-based index.
     */
    public static Index fromZeroBased(int zeroBasedIndex) throws CommandException {
        return new Index(zeroBasedIndex);
    }

    /**
     * Creates a new {@code Index} using a one-based index.
     */
    public static Index fromOneBased(int oneBasedIndex) throws CommandException {
        return new Index(oneBasedIndex - 1);
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
        return new ToStringBuilder(this).add("zeroBasedIndex", zeroBasedIndex).toString();
    }
}
