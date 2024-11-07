package seedu.address.model.person;

import java.util.Comparator;

/**
 * Contains a list of comparators for {@code Person}.
 */
public class PersonComparators {
    public static final Comparator<Person> BY_ORDER_ADDED = Comparator.comparing(Person::getDateAdded).reversed();
    public static final Comparator<Person> BY_ORDER_ADDED_REVERSED = Comparator.comparing(Person::getDateAdded);
    public static final Comparator<Person> BY_NAME = Comparator.comparing(Person::getName);
    public static final Comparator<Person> BY_NAME_REVERSED = Comparator.comparing(Person::getName).reversed();
}
