package seedu.address.model.client;

import java.util.function.Predicate;

/**
 * Tests that a {@code Client}'s {@code Phone} matches the given phone number.
 */
public class PhoneMatchesPredicate implements Predicate<Client> {
    private final String phone;

    public PhoneMatchesPredicate(String phone) {
        this.phone = phone.trim();
    }

    @Override
    public boolean test(Client client) {
        return client.getPhone().value.equals(phone);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PhoneMatchesPredicate
                && phone.equals(((PhoneMatchesPredicate) other).phone));
    }
}
