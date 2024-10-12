package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code PersonType} matches the given keyword.
 */
public class PersonFulfilsPredicate implements Predicate<Person> {
    private final String keyword;

    public PersonFulfilsPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Person person) {
        if (keyword.isEmpty()) {
            return true;
        }
        return person.getPersonType().value.toString().equals(keyword);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonFulfilsPredicate)) {
            return false;
        }

        PersonFulfilsPredicate otherPredicate = (PersonFulfilsPredicate) other;
        return keyword.equals(otherPredicate.keyword);
    }

}
