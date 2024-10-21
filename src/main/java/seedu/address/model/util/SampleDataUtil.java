package seedu.address.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Nric("S6283947C"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("diabetes", "hypertension"), getRoleSet("PATIENT")),
            new Person(new Name("Bernice Yu"), new Nric("S7012345B"), new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("arthritis", "medicationManagement"), getRoleSet("PATIENT")),
            new Person(new Name("Charlotte Oliveiro"), new Nric("S7193475F"), new Phone("93210283"),
                    new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("dementia", "fallRisk"), getRoleSet("PATIENT")),
            new Person(new Name("David Li"), new Nric("S6483749D"), new Phone("91031282"),
                    new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("chronicPain", "mobilityAid"), getRoleSet("PATIENT")),
            new Person(new Name("Irfan Ibrahim"), new Nric("S6382947A"), new Phone("92492021"),
                    new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("caregiverSupport", "medicationReminder"), getRoleSet("CAREGIVER")),
            new Person(new Name("Roy Balakrishnan"), new Nric("S6482983A"), new Phone("92624417"),
                    new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("caregiverSupport", "physiotherapyAssistance"), getRoleSet("CAREGIVER")),
            new Person(new Name("Sam Tan"), new Nric("S7382917H"), new Phone("91029382"),
                    new Email("samtan@example.com"),
                    new Address("Blk 123 Bukit Timah Street 32, #12-12"),
                    getTagSet("diabetes", "caregiverSupport"),
                    getRoleSet("PATIENT", "CAREGIVER"))

        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a role set containing the list of strings given.
     */
    public static Set<Role> getRoleSet(String... strings) {
        Set<Role> roleSet = new HashSet<>(); // Create an empty set to store Role enums

        // Loop through each string in the input
        for (String role : strings) {
            if (role != null) { // Check for null to avoid NullPointerException
                // Convert the string to uppercase and add the corresponding Role enum to the set
                roleSet.add(Role.valueOf(role.toUpperCase()));
            }
        }

        return roleSet; // Return the set containing Role enums
    }

    public static List<Note> getSampleNotes(String... notes) {

        List<Note> notesList = new ArrayList<>();

        for (String e: notes) {
            notesList.add(new Note(e));
        }

        return notesList;
    }
}
