package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Email} matches the given email.
 */
public class EmailMatchesPredicate implements Predicate<Person> {
    private final String email;

    public EmailMatchesPredicate(String email) {
        this.email = email.trim();
    }

    @Override
    public boolean test(Person person) {
        return person.getEmail().value.equalsIgnoreCase(email);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof EmailMatchesPredicate
                && email.equalsIgnoreCase(((EmailMatchesPredicate) other).email));
    }
}
