package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} and {@code Email} match the given name and email.
 */
public class NameEmailPredicate implements Predicate<Person> {
    private final String name;
    private final String email;

    /**
     * Constructs a {@code NameEmailPredicate}.
     *
     * @param name The name to match.
     * @param email The email to match.
     */
    public NameEmailPredicate(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Override
    public boolean test(Person person) {
        return person.getName().fullName.equalsIgnoreCase(name)
                && person.getEmail().value.equalsIgnoreCase(email);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof NameEmailPredicate)) {
            return false;
        }
        NameEmailPredicate otherPredicate = (NameEmailPredicate) other;
        return name.equalsIgnoreCase(otherPredicate.name)
                && email.equalsIgnoreCase(otherPredicate.email);
    }
}

