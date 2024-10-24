package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s VIP status is true.
 */
public class PersonIsVipPredicate implements Predicate<Person> {
    @Override
    public boolean test(Person person) {
        return person.isVip();
    }
}
