package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Represents a composite predicate that combines multiple predicates into one.
 */
public class CompositePredicate implements Predicate<Person> {
    private final List<Predicate<Person>> predicates;

    public CompositePredicate() {
        this.predicates = new ArrayList<>();
    }

    public void addPredicate(Predicate<Person> predicate) {
        predicates.add(predicate);
    }

    @Override
    public boolean test(Person person) {
        return predicates.stream().allMatch(predicate -> predicate.test(person));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CompositePredicate)) {
            return false;
        }
        CompositePredicate that = (CompositePredicate) other;
        return predicates.equals(that.predicates);
    }

    @Override
    public int hashCode() {
        return predicates.hashCode();
    }

    @Override
    public String toString() {
        return "CompositePredicate{" + "predicates=" + predicates + '}';
    }
}
