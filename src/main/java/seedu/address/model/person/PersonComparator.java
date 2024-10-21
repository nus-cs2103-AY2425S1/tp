package seedu.address.model.person;

import java.util.Comparator;

/**
 * Returns an appropriate comparator based on the person criteria being sorted by.
 */
public class PersonComparator {
    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";
    public static final String ADDRESS = "address";
    public static final String DATE_OF_LAST_VISIT = "dateoflastvisit";

    public static Comparator<Person> getComparator(String s) {
        if (s.equals(NAME)) {
            return new PersonNameComparator();
        }
    }

}

class PersonNameComparator implements Comparator<Person> {
    public int compare(Person p1, Person p2) {
        return p1.getName().fullName.compareTo(p2.getName().fullName);
    }
}

class PersonPhoneComparator implements Comparator<Person> {
    public int compare(Person p1, Person p2) {
        return p1.getPhone().value.compareTo(p2.getPhone().value);
    }
}

class PersonAddressComparator implements Comparator<Person> {
    public int compare(Person p1, Person p2) {
        return p1.getName().fullName.compareTo(p2.getName().fullName);
    }
}