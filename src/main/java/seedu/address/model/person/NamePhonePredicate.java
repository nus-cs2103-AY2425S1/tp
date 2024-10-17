package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} and {@code Phone} match the given name and phone.
 */
public class NamePhonePredicate implements Predicate<Person> {
    private final String name;
    private final String phone;

    /**
     * Constructs a {@code NamePhonePredicate}.
     *
     * @param name The name to match.
     * @param phone The phone number to match.
     */
    public NamePhonePredicate(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    @Override
    public boolean test(Person person) {
        return person.getName().fullName.equalsIgnoreCase(name)
                && person.getPhone().value.equals(phone);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof NamePhonePredicate)) {
            return false;
        }
        NamePhonePredicate otherPredicate = (NamePhonePredicate) other;
        return name.equalsIgnoreCase(otherPredicate.name)
                && phone.equals(otherPredicate.phone);
    }
}

