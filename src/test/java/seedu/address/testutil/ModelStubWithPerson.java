package seedu.address.testutil;

import seedu.address.model.person.Person;

import static java.util.Objects.requireNonNull;

/**
 * A Model stub that contains a single person.
 */
public class ModelStubWithPerson extends ModelStub {
    private final Person person;

    public ModelStubWithPerson(Person person) {
        requireNonNull(person);
        this.person = person;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return this.person.isSamePerson(person);
    }

    public boolean hasPhone(Person person) {
        requireNonNull(person);
        return this.person.hasSamePhone(person);
    }

    public boolean hasEmail(Person person) {
        requireNonNull(person);
        return this.person.hasSameEmail(person);
    }
}