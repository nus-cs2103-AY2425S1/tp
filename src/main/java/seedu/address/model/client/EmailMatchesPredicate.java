package seedu.address.model.client;

import java.util.function.Predicate;

/**
 * Tests that a {@code Client}'s {@code Email} matches the given email.
 */
public class EmailMatchesPredicate implements Predicate<Client> {
    private final String email;

    public EmailMatchesPredicate(String email) {
        this.email = email.trim();
    }

    @Override
    public boolean test(Client client) {
        return client.getEmail().value.equalsIgnoreCase(email);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof EmailMatchesPredicate
                && email.equalsIgnoreCase(((EmailMatchesPredicate) other).email));
    }
}
