package seedu.address.model.person;

import java.util.Comparator;

/**
 * Comparator to sort persons from high priority to low priority
 */
public class PriorityLowToHighComparator implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        return o2.getPriority().compareTo(o1.getPriority());
    }
}
