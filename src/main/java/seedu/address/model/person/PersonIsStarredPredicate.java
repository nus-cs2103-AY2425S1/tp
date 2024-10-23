package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code StarredStatus} is true.
 */
public class PersonIsStarredPredicate implements Predicate<Person> {

    @Override
    public boolean test(Person person) {
        return person.getStarredStatus().equals(new StarredStatus("true"));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonIsStarredPredicate)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
