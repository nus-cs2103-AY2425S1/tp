package seedu.address.model.patient;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

/**
 * Tests that a {@code Person} is an instanceof Patient.
 */
public class PatientPredicate implements Predicate<Person> {

    @Override
    public boolean test(Person person) {
        return person instanceof Patient;
    }
}
