package seedu.address.model.person;

import java.util.Objects;
import java.util.List;
import java.util.function.Predicate;

/**
 * Combines multiple predicates into one.
 */
public class CompositePredicate implements Predicate<Person> {
    private final List<Predicate<Person>> predicates;

    public CompositePredicate(List<Predicate<Person>> predicates) {
        this.predicates = predicates;
    }

    @Override
    public boolean test(Person person) {
        return predicates.stream().allMatch(predicate -> predicate.test(person));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompositePredicate // instanceof handles nulls
                && predicates.equals(((CompositePredicate) other).predicates));
    }

    @Override
    public int hashCode() {
        return Objects.hash(predicates);
    }
}
