package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person} is contained in the current filtered list.
 */
public class InFilteredListPredicate implements Predicate<Person> {
    private final List<Person> currentFilteredList;

    public InFilteredListPredicate(List<Person> currentFilteredList) {
        this.currentFilteredList = currentFilteredList;
    }

    @Override
    public boolean test(Person person) {
        return currentFilteredList.contains(person);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InFilteredListPredicate)) {
            return false;
        }

        InFilteredListPredicate otherInFilteredListPredicate = (InFilteredListPredicate) other;
        return currentFilteredList.equals(otherInFilteredListPredicate.currentFilteredList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("currentFilteredList", currentFilteredList).toString();
    }
}
