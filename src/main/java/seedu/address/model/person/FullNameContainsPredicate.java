package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} contains keywords given.
 */
public class FullNameContainsPredicate implements Predicate<Person> {
    private final String fullName;

    public FullNameContainsPredicate(String fullName) {
        this.fullName = fullName.toUpperCase();
    }

    @Override
    public boolean test(Person person) {
        return person.getName().fullName.toUpperCase().contains(fullName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FullNameContainsPredicate)) {
            return false;
        }

        FullNameContainsPredicate otherPredicate = (FullNameContainsPredicate) other;
        return fullName.equals(otherPredicate.fullName);
    }
}
