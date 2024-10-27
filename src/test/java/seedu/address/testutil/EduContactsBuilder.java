package seedu.address.testutil;

import seedu.address.model.EduContacts;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building EduContacts objects.
 * Example usage: <br>
 *     {@code EduContacts ab = new EduContactsBuilder().withPerson("John", "Doe").build();}
 */
public class EduContactsBuilder {

    private EduContacts eduContacts;

    public EduContactsBuilder() {
        eduContacts = new EduContacts();
    }

    public EduContactsBuilder(EduContacts eduContacts) {
        this.eduContacts = eduContacts;
    }

    /**
     * Adds a new {@code Person} to the {@code EduContacts} that we are building.
     */
    public EduContactsBuilder withPerson(Person person) {
        eduContacts.addPerson(person);
        return this;
    }

    public EduContacts build() {
        return eduContacts;
    }
}
