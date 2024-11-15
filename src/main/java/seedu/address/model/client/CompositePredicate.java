package seedu.address.model.client;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Combines multiple predicates into one.
 */
public class CompositePredicate implements Predicate<Client> {
    private final List<Predicate<Client>> predicates;

    public CompositePredicate(List<Predicate<Client>> predicates) {
        this.predicates = predicates;
    }

    @Override
    public boolean test(Client client) {
        return predicates.stream().allMatch(predicate -> predicate.test(client));
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
