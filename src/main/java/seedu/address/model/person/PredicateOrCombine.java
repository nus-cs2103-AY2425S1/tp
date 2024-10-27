package seedu.address.model.person;

import java.util.function.Predicate;

public class PredicateOrCombine implements Predicate<Person> {
    private final Predicate<Person> firstPredicate;
    private final Predicate<Person> secondPredicate;

    public PredicateOrCombine(Predicate<Person> firstPredicate, Predicate<Person> secondPredicate) {
        this.firstPredicate = firstPredicate;
        this.secondPredicate = secondPredicate;
    }

    @Override
    public boolean test(Person person) {
        return firstPredicate.test(person) || secondPredicate.test(person);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PredicateOrCombine)) {
            return false;
        }

        PredicateOrCombine otherPredicateOrCombine = (PredicateOrCombine) other;
        return firstPredicate.equals(otherPredicateOrCombine.firstPredicate)
                && secondPredicate.equals(otherPredicateOrCombine.secondPredicate);
    }

}
