package seedu.address.model.client.predicates;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.model.client.Client;

/**
 * Represents a predicate that combines multiple other predicates using the AND operator.
 * This class allows you to test multiple conditions on a {@link Client} object in a way that
 * ensures all provided predicates must evaluate to true for the overall predicate to return true.
 *
 * <p>This is particularly useful for filtering when you need to apply multiple conditions
 * simultaneously, such as filtering by name, phone number, email, etc.</p>
 */
public class CombinedPredicate implements Predicate<Client> {
    private final List<Predicate<Client>> predicates;

    /**
     * Constructs a {@code CombinedPredicate} with a list of predicates.
     * Each predicate in the list must evaluate to {@code true} for this combined predicate
     * to return {@code true} when tested.
     *
     * @param predicates the list of predicates to combine
     */
    public CombinedPredicate(List<Predicate<Client>> predicates) {
        this.predicates = Objects.requireNonNull(predicates, "Predicates list must not be null");
    }

    /**
     * Tests the given {@code Client} object against all the combined predicates.
     * This method returns {@code true} if all predicates return {@code true} for the given client.
     *
     * @param client the {@code Client} to be tested
     * @return {@code true} if all predicates return {@code true}; {@code false} otherwise
     */
    @Override
    public boolean test(Client client) {
        for (Predicate<Client> predicate : predicates) {
            if (!predicate.test(client)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether this combined predicate is equal to another object.
     * Two {@code CombinedPredicate} objects are considered equal if their underlying
     * list of predicates are equal. This method is used for ease of testing without dealing with lambdas.
     *
     * @param other the object to compare to
     * @return {@code true} if this combined predicate is equal to the other object; {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof CombinedPredicate)) {
            return false;
        }

        CombinedPredicate that = (CombinedPredicate) other;
        return this.predicates.equals(that.predicates);
    }

    // This method is used for ease of testing without dealing with lambdas.
    @Override
    public int hashCode() {
        return predicates.hashCode();
    }
}
