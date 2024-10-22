package seedu.address.model.person.comparator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Compare the {@code Github} of two {@code Person} based on the given sortOrder.
 */
public class PersonComparatorByGithub implements PersonComparator {
    private SortOrder sortOrder;

    PersonComparatorByGithub(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }
    @Override
    public String getSortField() {
        return "GITHUB";
    }

    @Override
    public String getSortOrder() {
        return sortOrder.toString();
    }

    @Override
    public int compare(Person o1, Person o2) {
        int comparison = o1.compareGithub(o2);

        return sortOrder == SortOrder.ASC ? comparison : -comparison;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonComparatorByGithub)) {
            return false;
        }

        PersonComparatorByGithub otherPersonComparatorByGithub = (PersonComparatorByGithub) other;
        return sortOrder.equals(otherPersonComparatorByGithub.sortOrder);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("sortOrder", sortOrder).toString();
    }
}
