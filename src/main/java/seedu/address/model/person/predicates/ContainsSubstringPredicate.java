package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Superclass for classes which test whether a {@code Person}'s attribute contains a specified substring.
 */
public abstract class ContainsSubstringPredicate implements Predicate<Person> {
    protected final String substring;

    public ContainsSubstringPredicate(String substring) {
        this.substring = substring.toUpperCase();
    }

    @Override
    public abstract boolean test(Person person);

    @Override
    public abstract boolean equals(Object other);
}
