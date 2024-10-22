package seedu.address.model.person.comparator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Compare the {@code Name} of two {@code Person} based on the given sortOrder.
 */
public class PersonComparatorByName implements PersonComparator {
    private SortOrder sortOrder;

    PersonComparatorByName(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }
    @Override
    public String getSortField() {
        return "NAME";
    }

    @Override
    public String getSortOrder() {
        return sortOrder.toString();
    }

    @Override
    public int compare(Person o1, Person o2) {
        int comparison = o1.compareName(o2);

        return sortOrder == SortOrder.ASC ? comparison : -comparison;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles null
        if (!(other instanceof PersonComparatorByName)) {
            return false;
        }

        PersonComparatorByName otherPersonComparatorByName = (PersonComparatorByName) other;
        return sortOrder.equals(otherPersonComparatorByName.sortOrder);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("sortOrder", sortOrder).toString();
    }

}
