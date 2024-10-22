package seedu.address.model.person;

import java.util.Comparator;

/**
 * Compares that a {@code Person}'s {@code Name} is bigger than the other.
 */
public class NameComparator implements Comparator<Person> {
    public NameComparator() {}

    @Override
    public int compare(Person person1, Person person2) {
        int comparison = person1.getName().fullName.compareTo(person2.getName().fullName);

        assert comparison != 0; // ensures no repeats of person

        return comparison;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NameComparator)) {
            return false;
        }

        return true;
    }
}
