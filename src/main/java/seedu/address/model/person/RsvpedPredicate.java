package seedu.address.model.person;

import java.util.function.Predicate;

public class RsvpedPredicate implements Predicate<Person> {

    @Override
    public boolean test(Person person) {
        return true; //person.getRsvp()
    }

}
