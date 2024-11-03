package seedu.address.model.person;

import java.util.Comparator;

/**
 * Comparator to sort persons from high priority to low priority
 */
public class PriorityHighToLowComparator implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        int comparisonResult = o1.getPriority().compareTo(o2.getPriority());

        if (comparisonResult != 0) {
            return comparisonResult;
        }

        // If same priority, compare by name for tie-breaker
        return o1.getName().compareTo(o2.getName());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof PriorityHighToLowComparator)) {
            return false;
        }

        return true;
    }
}
