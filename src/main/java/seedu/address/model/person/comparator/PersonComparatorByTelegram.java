package seedu.address.model.person.comparator;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Compare the {@code Telegram} of two {@code Person} based on the given sortOrder.
 */
public class PersonComparatorByTelegram implements PersonComparator {
    private SortOrder sortOrder;

    PersonComparatorByTelegram(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }
    @Override
    public String getSortField() {
        return "TELEGRAM";
    }

    @Override
    public String getSortOrder() {
        return sortOrder.toString();
    }

    @Override
    public int compare(Person o1, Person o2) {
        int comparison = o1.compareTelegram(o2);

        return sortOrder == SortOrder.ASC ? comparison : -comparison;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonComparatorByTelegram)) {
            return false;
        }

        PersonComparatorByTelegram otherPersonComparatorByTelegram = (PersonComparatorByTelegram) other;
        return sortOrder.equals(otherPersonComparatorByTelegram.sortOrder);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("sortOrder", sortOrder).toString();
    }
}
