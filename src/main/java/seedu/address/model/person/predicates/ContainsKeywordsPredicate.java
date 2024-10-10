package seedu.address.model.person.predicates;

import seedu.address.model.person.Person;

import java.util.function.Predicate;

public abstract class ContainsKeywordsPredicate implements Predicate<Person> {
    protected final String keyword;

    public ContainsKeywordsPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public abstract boolean test(Person person);

    @Override
    public abstract boolean equals(Object other);
}
