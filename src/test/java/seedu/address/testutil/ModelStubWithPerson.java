package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import seedu.address.model.person.Person;

/**
 * A Model stub that contains a single person.
 */
public class ModelStubWithPerson extends ModelStub {
    private final Person person;

    /**
     * Constructs a ModelStubWithPerson with the specified person.
     *
     * @param person The person to be contained in the model stub.
     */
    public ModelStubWithPerson(Person person) {
        requireNonNull(person);
        this.person = person;
    }

    /**
     * Checks if a person already exists in the list.
     *
     * @param person The person to check.
     * @return true if the person exists; otherwise, false.
     */
    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return this.person.isSamePerson(person);
    }

    /**
     * Checks if a person with the same phone number already exists in the list.
     *
     * @param person The person to check.
     * @return true if a person with the same phone number exists; otherwise, false.
     */
    public boolean hasPhone(Person person) {
        requireNonNull(person);
        return this.person.hasSamePhone(person);
    }

    /**
     * Checks if a person with the same email already exists in the list.
     *
     * @param person The person to check.
     * @return true if a person with the same email exists; otherwise, false.
     */
    public boolean hasEmail(Person person) {
        requireNonNull(person);
        return this.person.hasSameEmail(person);
    }
}
