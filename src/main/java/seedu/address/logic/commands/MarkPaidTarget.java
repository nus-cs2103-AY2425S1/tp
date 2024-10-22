package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;

/**
 * Represents the target of the MarkPaidCommand, which can be either an Index or 'all'.
 */
public class MarkPaidTarget {
    private final Index index;
    private final boolean markAll;

    private MarkPaidTarget(Index index, boolean markAll) {
        this.index = index;
        this.markAll = markAll;
    }

    public static MarkPaidTarget of(Index index) {
        return new MarkPaidTarget(index, false);
    }

    public static MarkPaidTarget all() {
        return new MarkPaidTarget(null, true);
    }

    public boolean markAll() {
        return markAll;
    }

    public Index getIndex() {
        if (markAll) {
            throw new IllegalStateException("Cannot get index when target is 'all'");
        }
        return index;
    }
    @Override
    public String toString() {
        if (markAll) {
            return "all";
        } else {
            return index.getOneBased() + "";
        }
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
        return markAll == otherTarget.markAll && java.util.Objects.equals(index, otherTarget.index);
    }
}
