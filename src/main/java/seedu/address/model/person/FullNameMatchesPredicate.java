package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} matches exactly with the given full name.
 */
public class FullNameMatchesPredicate implements Predicate<Person> {
    private final String fullName;

    public FullNameMatchesPredicate(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean test(Person person) {
        // Check if the person's full name matches exactly (ignoring case)
        return person.getName().fullName.equalsIgnoreCase(fullName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FullNameMatchesPredicate)) {
            return false;
        }

        FullNameMatchesPredicate otherPredicate = (FullNameMatchesPredicate) other;
        return fullName.equalsIgnoreCase(otherPredicate.fullName);
    }

    @Override
    public String toString() {
        return String.format("FullNameMatchesPredicate[fullName=%s]", fullName);
    }
}
