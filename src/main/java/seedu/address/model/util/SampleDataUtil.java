package seedu.address.model.util;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static final Remark EMPTY_REMARK = new Remark("");
    private static Person person1 = new Person(
            new Name("Alex Yeoh"),
            "PATIENT",
            new Phone("87438807"),
            new Email("alexyeoh@example.com"),
            new Address("Blk 30 Geylang Street 29, #06-40"),
            EMPTY_REMARK
    );

    private static Person person2 = new Person(
            new Name("Bernice Yu"),
            "PATIENT",
            new Phone("99272758"),
            new Email("berniceyu@example.com"),
            new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
            EMPTY_REMARK
    );

    private static Person person3 = new Person(
            new Name("Charlotte Oliveiro"),
            "PATIENT",
            new Phone("93210283"),
            new Email("charlotte@example.com"),
            new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
            EMPTY_REMARK
    );

    private static Person person4 = new Person(
            new Name("David Li"),
            "PATIENT",
            new Phone("91031282"),
            new Email("lidavid@example.com"),
            new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
            EMPTY_REMARK
    );

    private static Person person5 = new Person(
            new Name("Irfan Ibrahim"),
            "PATIENT",
            new Phone("92492021"),
            new Email("irfan@example.com"),
            new Address("Blk 47 Tampines Street 20, #17-35"),
            EMPTY_REMARK
    );

    private static Person person6 = new Person(
            new Name("Roy Balakrishnan"),
            "PATIENT",
            new Phone("92624417"),
            new Email("royb@example.com"),
            new Address("Blk 45 Aljunied Street 85, #11-31"),
            EMPTY_REMARK
    );



    public static Person[] getSamplePersons() {
        Person[] persons = new Person[] {person1, person2, person3, person4, person5, person6};
        return persons;
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

}
