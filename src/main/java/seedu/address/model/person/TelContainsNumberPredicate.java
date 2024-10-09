package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Predicate to test whether Person's phone number contain certain numbers
 */
public class TelContainsNumberPredicate implements Predicate<Person> {
    private final String searchTel;

    public TelContainsNumberPredicate(String searchTel) {
        this.searchTel = searchTel;
    }

    @Override
    public boolean test(Person person) {
        // make it so that the search does not require full name (albeit first or last)
        return person.getPhone().value.contains(searchTel);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TelContainsNumberPredicate)) {
            return false;
        }

        TelContainsNumberPredicate otherNameContainsTelPredicate = (TelContainsNumberPredicate) other;
        return searchTel.equals(otherNameContainsTelPredicate.searchTel);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("search telephone number", searchTel).toString();
    }
}
