package seedu.address.model.tag;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;
import seedu.address.commons.util.ToStringBuilder;

public class UniqueTagList {
    private final List<Tag> internalList = new ArrayList<>();

    public UniqueTagList() {}

    /**
     * Returns true if the list contains an equivalent tag as the given argument.
     */
    public boolean contains(Tag toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a tag to the list.
     * the tag does not exist in the list.
     */
    public void add(Tag toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            return;
        }
        internalList.add(toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(internalList).toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof UniqueTagList) {
            return ((UniqueTagList) other).internalList.equals(internalList);
        }
        return false;
    }
}