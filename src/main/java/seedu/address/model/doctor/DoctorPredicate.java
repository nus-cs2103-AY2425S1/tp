package seedu.address.model.doctor;

import java.util.function.Predicate;

import seedu.address.model.person.Person;

public class DoctorPredicate implements Predicate<Person> {
        @Override
        public boolean test(Person person) {
            return person instanceof Doctor;
        }
}
