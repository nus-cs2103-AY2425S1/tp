package seedu.hiredfiredpro.testutil;

import seedu.hiredfiredpro.model.HiredFiredPro;
import seedu.hiredfiredpro.model.person.Person;

/**
 * A utility class to help with building HiredFiredPro objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class HiredFiredProBuilder {

    private HiredFiredPro hiredFiredPro;

    public HiredFiredProBuilder() {
        hiredFiredPro = new HiredFiredPro();
    }

    public HiredFiredProBuilder(HiredFiredPro hiredFiredPro) {
        this.hiredFiredPro = hiredFiredPro;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public HiredFiredProBuilder withPerson(Person person) {
        hiredFiredPro.addPerson(person);
        return this;
    }

    public HiredFiredPro build() {
        return hiredFiredPro;
    }
}
