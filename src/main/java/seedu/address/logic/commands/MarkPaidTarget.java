package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents the target of the MarkPaidCommand, which can be either an Index or 'all'.
 */
public class MarkPaidTarget {
    private final Index index;
    private final boolean isMarkAll;

    private MarkPaidTarget(Index index, boolean isMarkAll) {
        this.index = index;
        this.isMarkAll = isMarkAll;
    }

    public static MarkPaidTarget fromIndex(Index index) {
        return new MarkPaidTarget(index, false);
    }

    public static MarkPaidTarget all() {
        return new MarkPaidTarget(null, true);
    }

    public boolean getMarkAll() {
        return isMarkAll;
    }

    public Index getIndex() {
        if (isMarkAll) {
            throw new IllegalStateException("Cannot get index when target is 'all'");
        }
        return index;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("markAll", isMarkAll)
                .add("index", isMarkAll ? "all" : index.getOneBased())
                .toString();
    }
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MarkPaidTarget)) {
            return false;
        }
        MarkPaidTarget otherTarget = (MarkPaidTarget) other;
        return isMarkAll == otherTarget.isMarkAll && index.equals(otherTarget.index);
    }
}
