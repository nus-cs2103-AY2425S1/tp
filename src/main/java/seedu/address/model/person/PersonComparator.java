package seedu.address.model.person;

import java.util.Comparator;

import seedu.address.logic.commands.exceptions.CommandException;


/**
 * Returns an appropriate comparator based on the person criteria being sorted by.
 */
public class PersonComparator {
    public static final String NAME = "name";
    public static final String DATE_OF_LAST_VISIT = "date of last visit";
    private static final String SORT_EXCEPTION = "The specified parameter is invalid.";

    public PersonComparator() {

    }

    /**
     * Returns an appropriate comparator in ascending order for comparing persons
     * according to the specified person parameter.
     *
     * @param parameter
     * @return Comparator
     */
    public Comparator<Person> getComparator(String parameter, boolean isAscending) throws CommandException {
        switch (parameter) {

        case NAME:
            if (isAscending) {
                return new PersonNameComparator();
            } else {
                return new PersonNameComparator().reversed();
            }

        case DATE_OF_LAST_VISIT:
            if (isAscending) {
                return new PersonDateOfLastVisitAscendingComparator();
            } else {
                return new PersonDateOfLastVisitDescendingComparator();
            }

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

/**
 * Represents a class for comparing persons by DateOfLastVisit.
 * Where the person has no date of last visit they will be in the back
 * of the list given ascending order.
 */
class PersonDateOfLastVisitAscendingComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        if (p1.getDateOfLastVisit().isEmpty() && p2.getDateOfLastVisit().isEmpty()) {
            return 0;
        }
        if (p1.getDateOfLastVisit().isEmpty()) {
            return 1;
        }
        if (p2.getDateOfLastVisit().isEmpty()) {
            return -1;
        }
        return p1.getDateOfLastVisit().get().compareTo(p2.getDateOfLastVisit().get());
    }
}

/**
 * Represents a class for comparing persons by DateOfLastVisit.
 * Where the person has no date of last visit they will be in the back
 * of the list given descending order.
 */
class PersonDateOfLastVisitDescendingComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        if (p1.getDateOfLastVisit().isEmpty() && p2.getDateOfLastVisit().isEmpty()) {
            return 0;
        }
        if (p1.getDateOfLastVisit().isEmpty()) {
            return 1;
        }
        if (p2.getDateOfLastVisit().isEmpty()) {
            return -1;
        }
        return p2.getDateOfLastVisit().get().compareTo(p1.getDateOfLastVisit().get());
    }
}
