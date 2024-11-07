package tuteez.model.person.predicates;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import tuteez.model.person.Person;

/**
 * Represents a composite predicate that combines multiple {@code Predicate<Person>} instances.
 * This predicate evaluates to true if any of the combined predicates return true for a given {@code Person}.
 */
public class CombinedPredicate implements Predicate<Person> {

    private final List<Predicate<Person>> predicates;

    /**
     * Constructs a {@code CombinedPredicate} with the given list of predicates.
     *
     * @param predicates A list of predicates to combine.
     */
    public CombinedPredicate(List<Predicate<Person>> predicates) {
        this.predicates = predicates;
    }

    @Override
    public boolean test(Person person) {
        return predicates.stream().anyMatch(predicate -> predicate.test(person));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CombinedPredicate)) {
            return false;
        }

        CombinedPredicate otherCombinedPredicate = (CombinedPredicate) other;
        return predicates.equals(otherCombinedPredicate.predicates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(predicates);
    }
}

