package seedu.address.logic.commands.wedding;

import java.util.Set;

import seedu.address.model.person.Person;
import seedu.address.model.person.Vendor;
import seedu.address.model.wedding.Wedding;

/**
 * Utility class to create the correct type of Person with new wedding set depending
 * on original type of Person.
 */
public class PersonWeddingUtil {
    public static Person getNewPerson(Person person, Set<Wedding> weddingSet) {
        Person newPerson;
        if (person instanceof Vendor) {
            newPerson = new Vendor(
                    person.getName(),
                    person.getPhone(),
                    person.getEmail(),
                    person.getAddress(),
                    person.getTags(),
                    weddingSet,
                    person.getTasks()
            );
        } else {
            newPerson = new Person(
                    person.getName(),
                    person.getPhone(),
                    person.getEmail(),
                    person.getAddress(),
                    person.getTags(),
                    weddingSet,
                    person.getTasks()
            );
        }
        return newPerson;
    }
}
