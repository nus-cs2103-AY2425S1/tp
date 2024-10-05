package seedu.researchroster.testutil;

import seedu.researchroster.model.ResearchRoster;
import seedu.researchroster.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code ResearchRoster ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private ResearchRoster researchRoster;

    public AddressBookBuilder() {
        researchRoster = new ResearchRoster();
    }

    public AddressBookBuilder(ResearchRoster researchRoster) {
        this.researchRoster = researchRoster;
    }

    /**
     * Adds a new {@code Person} to the {@code ResearchRoster} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        researchRoster.addPerson(person);
        return this;
    }

    public ResearchRoster build() {
        return researchRoster;
    }
}
