package seedu.academyassist.testutil;

import seedu.academyassist.model.AcademyAssist;
import seedu.academyassist.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AcademyAssistBuilder {

    private AcademyAssist academyAssist;

    public AcademyAssistBuilder() {
        academyAssist = new AcademyAssist();
    }

    public AcademyAssistBuilder(AcademyAssist academyAssist) {
        this.academyAssist = academyAssist;
    }

    /**
     * Adds a new {@code Person} to the {@code AcademyAssist} that we are building.
     */
    public AcademyAssistBuilder withPerson(Person person) {
        academyAssist.addPerson(person);
        return this;
    }

    public AcademyAssist build() {
        return academyAssist;
    }
}
