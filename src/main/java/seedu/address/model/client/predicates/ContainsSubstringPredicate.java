package seedu.address.model.client.predicates;

import java.util.function.Predicate;

import seedu.address.model.client.Client;

/**
 * Superclass for classes which test whether a {@code Client}'s attribute contains a specified substring.
 */
public abstract class ContainsSubstringPredicate implements Predicate<Client> {
    protected final String substring;

    public ContainsSubstringPredicate(String substring) {
        this.substring = substring.toUpperCase();
    }

    @Override
    public abstract boolean test(Client client);

    @Override
    public abstract boolean equals(Object other);
}
