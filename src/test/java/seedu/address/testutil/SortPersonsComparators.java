package seedu.address.testutil;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * A class containing a list of comparators to be used in tests.
 */
public class SortPersonsComparators {
    public static final Comparator<Person> COMPARATOR_FOR_NAME = Comparator.comparing(person
            -> person.getName().fullName);

    public static final Comparator<Person> COMPARATOR_FOR_ROLE = Comparator.comparing(person
            -> person.getRole().roleName);

    public static final Comparator<Person> COMPARATOR_FOR_PHONE = Comparator.comparing(person
            -> person.getPhone().value);

    public static final Comparator<Person> COMPARATOR_FOR_EMAIL = Comparator.comparing(person
            -> person.getEmail().value);

    public static final Comparator<Person> COMPARATOR_FOR_ADDRESS = Comparator.comparing(person
            -> person.getAddress().value);
}
