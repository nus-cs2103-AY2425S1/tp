package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Email} contains keywords given.
 */
public class EmailExactPredicate implements Predicate<Person> {
    private final String email;

    public EmailExactPredicate(String email) {
        this.email = email.toLowerCase();
    }

    @Override
    public boolean test(Person person) {
        return person.getEmail().value.toLowerCase().equals(email);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EmailPredicate)) {
            return false;
        }

        EmailExactPredicate otherPredicate = (EmailExactPredicate) other;
        return email.equals(otherPredicate.email);
    }
}
