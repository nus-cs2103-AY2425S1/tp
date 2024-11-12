package seedu.address.commons.core;

import java.io.Serializable;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.PersonComparator;

/**
 * A Serializable class that contains the Sort settings.
 * Guarantees: immutable.
 */
public class SortSettings implements Serializable {

    private static final String DEFAULT_SORT_PARAMETER = PersonComparator.NAME;
    private static final boolean DEFAULT_IS_ASCENDING_ORDER = true;

    private final String sortParameter;

    private final boolean isAscendingOrder;

    /**
     * Constructs a {@code SortSettings} with the default sortParameter and Order.
     */
    public SortSettings() {
        sortParameter = DEFAULT_SORT_PARAMETER;
        isAscendingOrder = DEFAULT_IS_ASCENDING_ORDER;
    }

    /**
     * Constructs a {@code SortSettings} with the specified sortParameter and Order.
     */
    public SortSettings(String sortParameter, boolean isAscendingOrder) {
        this.sortParameter = sortParameter;
        this.isAscendingOrder = isAscendingOrder;
    }

    public String getSortParameter() {
        return this.sortParameter;
    }

    public boolean isAscendingOrder() {
        return this.isAscendingOrder;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortSettings)) {
            return false;
        }

        SortSettings otherSortSettings = (SortSettings) other;
        return this.sortParameter == otherSortSettings.sortParameter
                && (this.isAscendingOrder == otherSortSettings.isAscendingOrder);

    }

    @Override
    public int hashCode() {
        return Objects.hash(sortParameter, isAscendingOrder);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("sortParameter", sortParameter)
                .add("isAscendingOrder", isAscendingOrder)
                .toString();
    }
}
