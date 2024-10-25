package seedu.address.testutil;

import java.util.Comparator;

import seedu.address.model.person.Person;

public class sortPersonsComparators {
    public static Comparator<Person> comparatorForName = Comparator.comparing(person
            -> person.getName().fullName);

    public static Comparator<Person> comparatorForRole = Comparator.comparing(person
            -> person.getRole().roleName);

    public static Comparator<Person> comparatorForPhone = Comparator.comparing(person
            -> person.getPhone().value);

    public static Comparator<Person> comparatorForEmail = Comparator.comparing(person
            -> person.getEmail().value);

    public static Comparator<Person> comparatorForAddress = Comparator.comparing(person
            -> person.getAddress().value);
}
