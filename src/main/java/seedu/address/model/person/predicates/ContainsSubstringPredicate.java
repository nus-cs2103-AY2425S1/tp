package seedu.address.model.person.predicates;

import seedu.address.model.person.Person;

import java.util.function.Predicate;

/**
 * Superclass for classes which test whether a {@code Person}'s attribute contains a specified substring.
 */
public abstract class ContainsSubstringPredicate implements Predicate<Person> {
    protected final String substring;

    public ContainsSubstringPredicate(String substring) {
        this.substring = substring;
    }

    @Override
    public abstract boolean test(Person person);

    @Override
    public abstract boolean equals(Object other);
}
