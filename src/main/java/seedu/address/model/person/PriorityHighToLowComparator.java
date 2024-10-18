package seedu.address.model.person;

import java.util.Comparator;

public class PriorityHighToLowComparator implements Comparator<Person> {

    @Override
    public int compare(Person o1, Person o2) {
        return o1.getPriority().compareTo(o2.getPriority());
    }
}
