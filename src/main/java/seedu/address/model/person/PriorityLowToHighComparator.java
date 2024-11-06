package seedu.address.model.person;

import java.util.Comparator;

/**
 * Comparator to sort persons from high priority to low priority
 */
public class PriorityLowToHighComparator implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        int priorityResult = o2.getPriority().compareTo(o1.getPriority());

        if (priorityResult != 0) {
            return priorityResult;
        }

        // If same priority, compare by email for tie-breaker
        return o1.getEmail().compareTo(o2.getEmail());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof PriorityLowToHighComparator)) {
            return false;
        }

        return true;
    }
}
