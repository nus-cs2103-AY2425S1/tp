package seedu.address.model.person.comparator;

import java.util.Comparator;

import seedu.address.model.person.Person;

public interface PersonComparator extends Comparator<Person> {
    String getSortField();
    String getSortOrder();
}
