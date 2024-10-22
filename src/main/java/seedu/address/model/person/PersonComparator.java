package seedu.address.model.person;

import seedu.address.logic.commands.exceptions.CommandException;

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
    public static final String LATEST_VALID_DATE = "31-12-9999";
    private static final String SORT_EXCEPTION = "The specified parameter is invalid.";

    public PersonComparator() {

    }

    /**
     * Returns an appropriate comparator in ascending order for comparing persons
     * according to the specified person parameter.
     *
     * @param Parameter
     * @return Comparator
     */
    public Comparator<Person> getComparator(String Parameter) throws CommandException {
        switch (Parameter) {

        case NAME:
            return new PersonNameComparator();

        case PHONE:
            return new PersonPhoneComparator();

        case EMAIL:
            return new PersonEmailComparator();

        case ADDRESS:
            return new PersonAddressComparator();

        case DATE_OF_LAST_VISIT:
            return new PersonDateOfLastVisitComparator();

        default:
            throw new CommandException(SORT_EXCEPTION);
        }
    }

}

/**
 * Represents a comparator for comparing persons full name (case-insensitive)
 * in ascending order.
 */
class PersonNameComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        return p1.getName().fullName.toLowerCase().compareTo(p2.getName().fullName.toLowerCase());
    }
}

class PersonPhoneComparator implements Comparator<Person> {
    public int compare(Person p1, Person p2) {
        return p1.getPhone().value.compareTo(p2.getPhone().value);
    }
}

class PersonAddressComparator implements Comparator<Person> {
    public int compare(Person p1, Person p2) {
        return p1.getAddress().orElse(new Address(PersonComparator.LATEST_VALID_DATE)).value
                .compareTo(p2.getAddress().orElse(new Address(PersonComparator.LATEST_VALID_DATE)).value);
    }
}

class PersonEmailComparator implements Comparator<Person> {
    public int compare(Person p1, Person p2) {
        return p1.getEmail().orElse(new Email(PersonComparator.LATEST_VALID_DATE)).value
                .compareTo(p2.getEmail().orElse(new Email(PersonComparator.LATEST_VALID_DATE)).value);
    }
}

/**
 * Represents a class for comparing persons by DateOfLastVisit.
 * Where the person has no date of last visit they will be in the back
 * of the list given ascending order.
 */
class PersonDateOfLastVisitComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        return p1.getDateOfLastVisit().orElse(new DateOfLastVisit(PersonComparator.LATEST_VALID_DATE)).value
                .compareTo(p2.getDateOfLastVisit().orElse(new DateOfLastVisit(PersonComparator.LATEST_VALID_DATE)).value);
    }
}